package com.github.alaanor.candid

import com.github.alaanor.candid.psi.primitive.CandidNamedElement
import com.intellij.lang.refactoring.RefactoringSupportProvider
import com.intellij.psi.PsiElement

class CandidRefactoringSupportProvider: RefactoringSupportProvider() {
    override fun isMemberInplaceRenameAvailable(element: PsiElement, context: PsiElement?): Boolean {
        return element is CandidNamedElement
    }
}