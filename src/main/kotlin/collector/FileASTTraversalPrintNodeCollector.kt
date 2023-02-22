package collector

import collector.TreeNode.FileASTTreeNode
import org.jetbrains.kotlin.backend.common.push
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.PsiFile
import org.jetbrains.kotlin.psi.psiUtil.children

internal class FileASTTraversalPrintNodeCollector : PrintNodeCollector {

    override fun collect(psiFile: PsiFile): List<PrintNode> {
        return ArrayDeque<PrintNode>().apply {
            collectInto(this, psiFile.node, ZERO_DEPTH)
        }
    }

    private fun collectInto(list: MutableList<PrintNode>, node: ASTNode, depth: Int) {
        list.push(PrintNode(depth, FileASTTreeNode(node)))

        for (child in node.children()) {
            collectInto(list, child, depth.inc())
        }
    }
}
