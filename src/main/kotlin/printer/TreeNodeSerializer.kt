package printer

import collector.TreeNode.*

internal class TreeNodeSerializer {

    fun serialize(treeNode: FileASTTreeNode): String {
        return treeNode.node.toString()
    }

    fun serialize(treeNode: PsiElementTreeNode): String {
        return treeNode.element.toString()
    }

    fun serialize(treeNode: KtElementTreeNode): String {
        return treeNode.element.toString()
    }
}
