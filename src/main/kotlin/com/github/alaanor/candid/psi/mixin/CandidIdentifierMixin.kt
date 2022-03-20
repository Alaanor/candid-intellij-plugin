package com.github.alaanor.candid.psi.mixin

import com.github.alaanor.candid.psi.CandidIdentifier
import com.github.alaanor.candid.psi.primitive.CandidNamedElementBase
import com.github.alaanor.candid.reference.CandidIdentifierReference
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiReference

abstract class CandidIdentifierMixin(node: ASTNode) : CandidNamedElementBase(node), CandidIdentifier {
    override fun getReference(): PsiReference? {
        return CandidIdentifierReference(this)
    }
}