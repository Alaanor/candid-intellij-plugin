package com.github.alaanor.candid.psi.primitive

import com.github.alaanor.candid.CandidTypes
import com.github.alaanor.candid.psi.CandidElementFactory
import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement

abstract class CandidNamedElementBase(node: ASTNode) : ASTWrapperPsiElement(node), CandidNamedElement {
    override fun getNameIdentifier(): PsiElement? = findChildByType(CandidTypes.ID)
    override fun getName(): String? = nameIdentifier?.text
    override fun setName(name: String): PsiElement {
        nameIdentifier?.replace(CandidElementFactory.createDefinition(project, name))
        return this
    }
}