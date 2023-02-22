package collector

import collector.TreeNode.KtElementTreeNode
import org.jetbrains.kotlin.backend.common.push
import org.jetbrains.kotlin.com.intellij.psi.PsiFile
import org.jetbrains.kotlin.psi.KtElement
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtTreeVisitorVoid

internal class KtVisitorPrintNodeCollector : PrintNodeCollector {

    override fun collect(psiFile: PsiFile): List<PrintNode> {
        return ArrayDeque<PrintNode>().apply {
            psiFile.accept(KtVisitor(this))
        }
    }

    private class KtVisitor(
        private val list: MutableList<PrintNode>,
    ) : KtTreeVisitorVoid() {

        private var depth = ZERO_DEPTH

        override fun visitKtFile(file: KtFile) {
            list.push(PrintNode(depth, KtElementTreeNode(file)))
            depth++
            super.visitKtFile(file)
        }

        override fun visitKtElement(element: KtElement) {
            list.push(PrintNode(depth, KtElementTreeNode(element)))
            depth++
            super.visitKtElement(element)
            depth--
        }
    }
}