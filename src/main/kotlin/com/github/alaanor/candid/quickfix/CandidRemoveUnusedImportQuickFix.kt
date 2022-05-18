package com.github.alaanor.candid.quickfix

import com.github.alaanor.candid.psi.CandidImportStatement
import com.github.alaanor.candid.psi.deleteWithSurroundSemicolon
import com.intellij.codeInspection.LocalQuickFixOnPsiElement
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile

class CandidRemoveUnusedImportQuickFix(import: CandidImportStatement) : LocalQuickFixOnPsiElement(import) {
    override fun getText(): String = "Remove unused import"
    override fun getFamilyName(): String = text

    override fun invoke(project: Project, file: PsiFile, startElement: PsiElement, endElement: PsiElement) {
        val element = startElement as? CandidImportStatement ?: return
        element.deleteWithSurroundSemicolon()
    }
}