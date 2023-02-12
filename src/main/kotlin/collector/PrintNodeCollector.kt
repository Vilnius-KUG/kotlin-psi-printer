package collector

import org.jetbrains.kotlin.com.intellij.psi.PsiFile

internal const val ZERO_DEPTH = 0

internal interface PrintNodeCollector {

    fun collect(psiFile: PsiFile): List<PrintNode>
}
