import cli.TraversalType
import collector.FileASTTraversalPrintNodeCollector
import collector.KtVisitorPrintNodeCollector
import collector.PrintNodeCollector
import collector.PsiTraversalPrintNodeCollector
import collector.PsiVisitorPrintNodeCollector
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default
import kotlinx.cli.multiple
import kotlinx.cli.required
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.jetbrains.kotlin.com.intellij.openapi.project.Project
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtPsiFactory
import printer.PrintNodeSerializer
import printer.Printer
import printer.TreeNodeSerializer
import java.io.File
import java.io.PrintWriter

fun main(args: Array<String>) {
    val argParser = ArgParser("psi-printer")
    val inputFilePath by argParser.option(
        type = ArgType.String,
        fullName = "path",
        shortName = "p",
        description = "Path to Kotlin file, which syntax tree needs to be printed.",
    ).required()
    val traversalMethod by argParser.option(
        type = ArgType.Choice<TraversalType>(),
        fullName = "traversal-type",
        shortName = "t",
        description = "Type of the abstract tree traversal to use."
    ).default(TraversalType.AST)
        .multiple()

    argParser.parse(args)

    val disposable = Disposer.newDisposable()
    try {
        val project = KotlinCoreEnvironment.createForProduction(
            disposable,
            CompilerConfiguration(),
            EnvironmentConfigFiles.JVM_CONFIG_FILES,
        ).project
        val ktFile = createKtFile(inputFilePath, project)

        val treeNodeSerializer = TreeNodeSerializer()
        val printNodeSerializer = PrintNodeSerializer(treeNodeSerializer)
        val printWriter = PrintWriter(System.out)
        val printer = Printer(printWriter, printNodeSerializer)

        traversalMethod
            .forEach { type ->
                println("Printing file using $type method...")

                val collector = createCollectorForType(type)
                val nodes = collector.collect(ktFile)

                printer.print(nodes)

                println("\n")
            }
    } finally {
        disposable.dispose()
    }
}

private fun createKtFile(inputFilePath: String, project: Project): KtFile {
    val ktFileFactory = KtPsiFactory(project)
    val codeFile = File(inputFilePath)

    return ktFileFactory.createPhysicalFile(
        fileName = codeFile.name,
        text = codeFile.readText(),
    )
}

private fun createCollectorForType(type: TraversalType): PrintNodeCollector {
    return when (type) {
        TraversalType.AST -> FileASTTraversalPrintNodeCollector()
        TraversalType.PSI -> PsiTraversalPrintNodeCollector()
        TraversalType.PSI_VISITOR -> PsiVisitorPrintNodeCollector()
        TraversalType.KT_VISITOR -> KtVisitorPrintNodeCollector()
    }
}
