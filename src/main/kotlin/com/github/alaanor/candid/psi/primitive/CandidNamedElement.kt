package com.github.alaanor.candid.psi.primitive

import com.github.alaanor.candid.CandidTypes
import com.github.alaanor.candid.psi.CandidElementFactory
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNameIdentifierOwner
import com.intellij.psi.PsiNamedElement

interface CandidNamedElement : PsiNamedElement
interface CandidNameIdentifierOwner : CandidNamedElement, PsiNameIdentifierOwner

abstract class CandidNameIdentifierOwnerImpl(node: ASTNode) : CandidElementBase(node), CandidNameIdentifierOwner {

    override fun getNameIdentifier(): PsiElement? = findChildByType(CandidTypes.IDENTIFIER_DECLARATION)
    override fun getTextOffset(): Int = nameIdentifier?.textOffset ?: super.getTextOffset()
    override fun getName(): String? = nameIdentifier?.text

    override fun setName(name: String): PsiElement {
        nameIdentifier?.replace(CandidElementFactory.createDefinition(project, name))
        return this
    }
}