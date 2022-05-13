package com.github.alaanor.candid.formatter

import com.github.alaanor.candid.CandidTypes
import com.intellij.formatting.*
import com.intellij.lang.ASTNode
import com.intellij.psi.TokenType
import com.intellij.psi.formatter.common.AbstractBlock
import com.intellij.util.containers.addIfNotNull

class CandidBlock(node: ASTNode, wrap: Wrap?, alignment: Alignment?, private val spacingBuilder: SpacingBuilder) :
    AbstractBlock(node, wrap, alignment) {

    override fun getSpacing(child1: Block?, child2: Block): Spacing? {
        return spacingBuilder.getSpacing(this, child1, child2)
    }

    override fun isLeaf(): Boolean = node.firstChildNode == null

    override fun buildChildren(): MutableList<Block> {
        val blocks = mutableListOf<Block>()
        var child = node.firstChildNode
        while (child != null) {
            blocks.addIfNotNull(buildChild(child))
            child = child.treeNext
        }
        return blocks
    }

    private fun buildChild(child: ASTNode): Block? {
        return when (child.elementType) {
            TokenType.WHITE_SPACE -> null
            else -> CandidBlock(child, null, null, spacingBuilder)
        }
    }

    override fun getIndent(): Indent? {
        return when (node.elementType) {
            CandidTypes.FIELD_TYPE_RECORD,
            CandidTypes.FIELD_TYPE_VARIANT,
            CandidTypes.METH_TYPE,
            CandidTypes.BLOCK_COMMENT,
            CandidTypes.LINE_COMMENT -> {
                Indent.getNormalIndent()
            }

            else -> {
                Indent.getNoneIndent()
            }
        }
    }

    override fun getChildAttributes(newChildIndex: Int): ChildAttributes {
        return when (node.elementType) {
            CandidTypes.RECORD_STATEMENT,
            CandidTypes.VARIANT_STATEMENT,
            CandidTypes.METH_TYPE -> {
                ChildAttributes(Indent.getNormalIndent(), null)
            }

            else -> {
                ChildAttributes(Indent.getNoneIndent(), null)
            }
        }
    }
}