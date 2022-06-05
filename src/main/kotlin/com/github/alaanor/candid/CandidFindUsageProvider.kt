package com.github.alaanor.candid

import com.github.alaanor.candid.psi.CandidIdentifierDeclaration
import com.github.alaanor.candid.psi.CandidIdentifierReference
import com.github.alaanor.candid.psi.CandidMethodType
import com.intellij.lang.findUsages.FindUsagesProvider
import com.intellij.psi.PsiElement

class CandidFindUsageProvider : FindUsagesProvider {
    override fun canFindUsagesFor(psiElement: PsiElement): Boolean {
        return psiElement is CandidIdentifierDeclaration
                || psiElement is CandidIdentifierReference
                || psiElement is CandidMethodType
    }

    override fun getHelpId(psiElement: PsiElement): String? = null
    override fun getType(element: PsiElement): String = getNodeText(element, false)
    override fun getDescriptiveName(element: PsiElement): String = getNodeText(element, true)

    override fun getNodeText(element: PsiElement, useFullName: Boolean): String {
        return when (element) {
            is CandidIdentifierDeclaration -> "Type declaration"
            is CandidIdentifierReference -> "Type reference"
            is CandidMethodType -> "Method"
            else -> "Unknown node"
        }
    }
}