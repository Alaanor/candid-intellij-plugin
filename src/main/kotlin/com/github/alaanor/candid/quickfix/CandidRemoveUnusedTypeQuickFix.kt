package com.github.alaanor.candid.quickfix

import com.github.alaanor.candid.psi.CandidDefinition
import com.github.alaanor.candid.psi.CandidIdentifierDeclaration
import com.github.alaanor.candid.psi.deleteWithSurroundSemicolon
import com.intellij.codeInspection.LocalQuickFixOnPsiElement
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile

class CandidRemoveUnusedTypeQuickFix(import: CandidIdentifierDeclaration) : LocalQuickFixOnPsiElement(import) {
    override fun getText(): String = "Remove unused type"
    override fun getFamilyName(): String = text

    override fun invoke(project: Project, file: PsiFile, startElement: PsiElement, endElement: PsiElement) {
        val element = startElement as? CandidIdentifierDeclaration ?: return
        val definition = element.parent as? CandidDefinition ?: return
        definition.deleteWithSurroundSemicolon()
    }
}