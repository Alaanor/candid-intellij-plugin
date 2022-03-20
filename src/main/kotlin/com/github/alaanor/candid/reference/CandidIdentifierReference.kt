package com.github.alaanor.candid.reference

import com.github.alaanor.candid.psi.CandidIdentifier
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReferenceBase
import com.intellij.psi.util.PsiTreeUtil

class CandidIdentifierReference(identifier: CandidIdentifier) : PsiReferenceBase<CandidIdentifier>(identifier) {

    override fun calculateDefaultRangeInElement(): TextRange {
        return TextRange.create(0, element.textLength)
    }

    override fun resolve(): PsiElement? {
        return PsiTreeUtil.findChildrenOfType(element.containingFile, CandidIdentifier::class.java)
            .find { element.name == it.name }
    }

    override fun handleElementRename(newElementName: String): PsiElement {
        return element.setName(newElementName)
    }
}