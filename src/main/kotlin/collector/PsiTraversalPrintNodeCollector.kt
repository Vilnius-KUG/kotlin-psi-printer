package collector

import collector.TreeNode.PsiElementTreeNode
import org.jetbrains.kotlin.backend.common.push
import org.jetbrains.kotlin.com.intellij.psi.PsiElement
import org.jetbrains.kotlin.com.intellij.psi.PsiFile

internal class PsiTraversalPrintNodeCollector : PrintNodeCollector {

    override fun collect(psiFile: PsiFile): List<PrintNode> {
        return ArrayDeque<PrintNode>().apply {
            collectInto(this, psiFile, ZERO_DEPTH)
        }
    }

    private fun collectInto(list: MutableList<PrintNode>, node: PsiElement, depth: Int) {
        list.push(PrintNode(depth, PsiElementTreeNode(node)))

        for (child in node.children) {
            collectInto(list, child, depth.inc())
        }
    }
}
