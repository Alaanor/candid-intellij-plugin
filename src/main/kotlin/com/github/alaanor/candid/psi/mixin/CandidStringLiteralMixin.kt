package com.github.alaanor.candid.psi.mixin

import com.github.alaanor.candid.psi.CandidStringLiteral
import com.github.alaanor.candid.psi.primitive.CandidElementBase
import com.intellij.lang.ASTNode
import com.intellij.openapi.util.TextRange

abstract class CandidStringLiteralMixin(node: ASTNode): CandidElementBase(node), CandidStringLiteral {
    override fun getTextRange(): TextRange {
        return TextRange.create(node.textRange.startOffset + 1, node.textRange.endOffset - 1)
    }
}