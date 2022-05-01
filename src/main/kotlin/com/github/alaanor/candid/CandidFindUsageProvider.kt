package com.github.alaanor.candid

import com.github.alaanor.candid.psi.CandidIdentifierDeclaration
import com.github.alaanor.candid.psi.primitive.CandidNamedElement
import com.intellij.lang.findUsages.FindUsagesProvider
import com.intellij.psi.PsiElement

class CandidFindUsageProvider : FindUsagesProvider {
    override fun canFindUsagesFor(psiElement: PsiElement): Boolean = psiElement is CandidNamedElement
    override fun getHelpId(psiElement: PsiElement): String? = null
    override fun getType(element: PsiElement): String = "Candid type"
    override fun getDescriptiveName(element: PsiElement): String = getNodeText(element, true)

    override fun getNodeText(element: PsiElement, useFullName: Boolean): String {
        return when (element) {
            is CandidIdentifierDeclaration -> "Type declaration"
            else -> "Unknown node"
        }
    }
}