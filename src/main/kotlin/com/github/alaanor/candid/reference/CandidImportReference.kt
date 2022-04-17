package com.github.alaanor.candid.reference

import com.github.alaanor.candid.psi.CandidImportStatement
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiManager
import com.intellij.psi.PsiReferenceBase

class CandidImportReference(importStatement: CandidImportStatement, textRange: TextRange) :
    PsiReferenceBase<CandidImportStatement>(importStatement, textRange) {

    override fun resolve(): PsiElement? {
        if (element.stringLiteral == null) {
            return null
        }

        val targetName = element.stringLiteral!!.text.trim('"')
        val target = element.containingFile.virtualFile.findFileByRelativePath("../$targetName")
        return target?.let { PsiManager.getInstance(element.project).findFile(it) }
    }
}