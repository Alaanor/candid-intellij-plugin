package com.github.alaanor.candid.quickfix.action

import com.github.alaanor.candid.psi.CandidFile
import com.github.alaanor.candid.psi.CandidIdentifierDeclaration
import com.github.alaanor.candid.util.projectFilePath
import com.intellij.codeInsight.hint.QuestionAction
import com.intellij.icons.AllIcons
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.ui.popup.PopupStep
import com.intellij.openapi.ui.popup.util.BaseListPopupStep
import com.intellij.psi.PsiDocumentManager
import com.intellij.ui.popup.list.ListPopupImpl
import javax.swing.Icon

class CandidAddImportAction(
    private val editor: Editor,
    private val file: CandidFile,
    private val suggestions: List<CandidIdentifierDeclaration>
) : QuestionAction {
    override fun execute(): Boolean {
        PsiDocumentManager.getInstance(file.project).commitAllDocuments()

        when (suggestions.size) {
            1 -> file.addImportFor(file)
            else -> chooseFileToImport()
        }

        return true
    }

    private fun chooseFileToImport() {
        val step = object : BaseListPopupStep<CandidIdentifierDeclaration>(
            "File to Import",
            suggestions.toMutableList()
        ) {
            override fun getTextFor(value: CandidIdentifierDeclaration?): String {
                if (value == null)
                    return "Unknown"
                return "${value.name} (${value.projectFilePath()})"
            }

            override fun getIconFor(value: CandidIdentifierDeclaration?): Icon? {
                return AllIcons.Nodes.Type
            }

            override fun onChosen(selectedValue: CandidIdentifierDeclaration, finalChoice: Boolean): PopupStep<*>? {
                return doFinalStep {
                    PsiDocumentManager.getInstance(file.project).commitAllDocuments()
                    WriteCommandAction.runWriteCommandAction(file.project) {
                        file.addImportFor(selectedValue)
                    }
                }
            }
        }

        val popup = object : ListPopupImpl(file.project, step) {}
        popup.showInBestPositionFor(editor)
    }
}