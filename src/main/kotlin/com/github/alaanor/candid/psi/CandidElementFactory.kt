package com.github.alaanor.candid.psi

import com.github.alaanor.candid.CandidFileType
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory

object CandidElementFactory {
    fun createDefinition(project: Project, name: String): PsiElement {
        return createDummyFile(project, name).firstChild
    }

    fun createTypeReference(project: Project, name: String): PsiElement {
        return createDummyFile(project, "type foo = $name").firstChild.lastChild.lastChild
    }

    private fun createDummyFile(project: Project, text: String): CandidFile = PsiFileFactory.getInstance(project)
        .createFileFromText("dummy.candid", CandidFileType.INSTANCE, text) as CandidFile
}