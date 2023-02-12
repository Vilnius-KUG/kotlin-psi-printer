package collector

import printer.TreeNodeSerializer
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.PsiElement
import org.jetbrains.kotlin.psi.KtElement

internal sealed interface TreeNode {

    fun accept(stringifier: TreeNodeSerializer): String

    @JvmInline
    value class FileASTTreeNode(
        val node: ASTNode,
    ) : TreeNode {

        override fun accept(stringifier: TreeNodeSerializer): String {
            return stringifier.serialize(this)
        }
    }

    @JvmInline
    value class PsiElementTreeNode(
        val element: PsiElement,
    ) : TreeNode {

        override fun accept(stringifier: TreeNodeSerializer): String {
            return stringifier.serialize(this)
        }
    }

    @JvmInline
    value class KtElementTreeNode(
        val element: KtElement,
    ) : TreeNode {

        override fun accept(stringifier: TreeNodeSerializer): String {
            return stringifier.serialize(this)
        }
    }
}
