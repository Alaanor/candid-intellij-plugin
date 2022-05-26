package com.github.alaanor.candid.psi.mixin


import com.github.alaanor.candid.icon.CandidIcons
import com.github.alaanor.candid.psi.CandidIdentifierReference
import com.github.alaanor.candid.psi.primitive.CandidElementBase
import com.github.alaanor.candid.reference.CandidTypeReference
import com.intellij.lang.ASTNode
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiReference
import javax.swing.Icon

abstract class CandidIdentifierReferenceMixin(node: ASTNode) : CandidElementBase(node), CandidIdentifierReference {
    override fun getReference(): PsiReference? {
        return CandidTypeReference(this, TextRange.create(0, textLength))
    }

    override fun getIcon(flags: Int): Icon = CandidIcons.Type
}