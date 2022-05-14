package com.github.alaanor.candid.action

import com.github.alaanor.candid.icon.CandidIcons
import com.intellij.ide.actions.CreateFileFromTemplateAction
import com.intellij.ide.actions.CreateFileFromTemplateDialog
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory

class CandidCreateFileAction : CreateFileFromTemplateAction(Caption, "", CandidIcons.FileType), DumbAware {

    companion object {
        private const val Caption = "Candid File"
    }

    override fun getActionName(directory: PsiDirectory?, newName: String, templateName: String?): String = Caption

    override fun buildDialog(project: Project, directory: PsiDirectory, builder: CreateFileFromTemplateDialog.Builder) {
        builder
            .setTitle(Caption)
            .addKind("Empty file", CandidIcons.FileType, "CandidFile")
    }

}