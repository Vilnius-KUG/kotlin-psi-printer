package printer

import collector.PrintNode

internal class PrintNodeSerializer(
    private val treeNodeSerializer: TreeNodeSerializer,
) {

    fun serialize(printNode: PrintNode): String {
        val nodeString = printNode.treeNode.accept(treeNodeSerializer)
        return format(printNode.depth, nodeString)
    }

    private fun format(depth: Int, nodeString: String): String {
        return buildString {
            if (depth > 0) {
                append("|")
            }
            repeat(depth) {
                append("-")
            }
            append(nodeString)
        }
    }
}