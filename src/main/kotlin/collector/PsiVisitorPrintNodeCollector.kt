package collector

import collector.TreeNode.PsiElementTreeNode
import org.jetbrains.kotlin.backend.common.push
import org.jetbrains.kotlin.com.intellij.psi.PsiElement
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor
import org.jetbrains.kotlin.com.intellij.psi.PsiFile

internal class PsiVisitorPrintNodeCollector : PrintNodeCollector {

    override fun collect(psiFile: PsiFile): List<PrintNode> {
        return ArrayDeque<PrintNode>().apply {
            psiFile.accept(PsiVisitor(this))
        }
    }

    private class PsiVisitor(
        private val list: MutableList<PrintNode>,
    ) : PsiElementVisitor() {

        private var depth = ZERO_DEPTH

        override fun visitElement(element: PsiElement) {
            list.push(PrintNode(depth, PsiElementTreeNode(element)))
            depth++
            element.acceptChildren(this)
            depth--
        }
    }
}
