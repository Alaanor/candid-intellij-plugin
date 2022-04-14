package com.github.alaanor.candid.psi.mixin

import com.github.alaanor.candid.psi.CandidElementFactory
import com.github.alaanor.candid.psi.CandidIdentifierDeclaration
import com.github.alaanor.candid.psi.primitive.CandidElementBase
import com.github.alaanor.candid.psi.primitive.CandidNameIdentifierOwner
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement

abstract class CandidIdentifierDeclarationMixin(node: ASTNode) : CandidElementBase(node),
    CandidNameIdentifierOwner,
    CandidIdentifierDeclaration {

    override fun getNameIdentifier(): PsiElement = node.psi
    override fun getTextOffset(): Int = node.startOffset
    override fun getName(): String? = node.text

    override fun setName(name: String): PsiElement {
        nameIdentifier.replace(CandidElementFactory.createTypeDeclaration(project, name))
        return this
    }
}