package com.github.alaanor.candid.psi.mixin

import com.github.alaanor.candid.psi.CandidImportStatement
import com.github.alaanor.candid.psi.primitive.CandidElementBase
import com.github.alaanor.candid.reference.CandidImportReference
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiReference

abstract class CandidImportStatementMixin(node: ASTNode) : CandidElementBase(node), CandidImportStatement {
    override fun getReference(): PsiReference? {
        return stringLiteral?.textRange?.let {
            CandidImportReference(this, it)
        }
    }
}