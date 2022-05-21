package com.github.alaanor.candid.quickfix

import com.github.alaanor.candid.psi.CandidImportStatement
import com.github.alaanor.candid.psi.deleteWithSurroundSemicolon
import com.intellij.codeInspection.LocalQuickFixOnPsiElement
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile

class CandidRemoveImportQuickFix(import: CandidImportStatement, val type: Type) : LocalQuickFixOnPsiElement(import) {

    enum class Type {
        Invalid,
        Unused,
    }

    override fun getText(): String {
        return when (type) {
            Type.Invalid -> "Remove invalid import"
            Type.Unused -> "Remove unused import"
        }
    }
    override fun getFamilyName(): String = "Remove import"

    override fun invoke(project: Project, file: PsiFile, startElement: PsiElement, endElement: PsiElement) {
        val element = startElement as? CandidImportStatement ?: return
        element.deleteWithSurroundSemicolon()
    }
}