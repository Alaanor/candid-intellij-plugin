package com.github.alaanor.candid.quickfix

import com.github.alaanor.candid.psi.CandidFile
import com.github.alaanor.candid.psi.CandidIdentifierDeclaration
import com.github.alaanor.candid.psi.CandidIdentifierReference
import com.github.alaanor.candid.psi.stub.index.CandidStubTypeIndex
import com.github.alaanor.candid.quickfix.action.CandidAddImportAction
import com.github.alaanor.candid.util.getRelativePath
import com.intellij.codeInsight.hint.HintManager
import com.intellij.codeInsight.intention.HighPriorityAction
import com.intellij.codeInsight.intention.impl.BaseIntentionAction
import com.intellij.codeInspection.HintAction
import com.intellij.openapi.command.CommandProcessor
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.StubIndex
import com.intellij.refactoring.suggested.endOffset
import com.intellij.refactoring.suggested.startOffset

class CandidAddImportQuickFix(private val unresolved: CandidIdentifierReference) : BaseIntentionAction(), HintAction,
    HighPriorityAction {

    private lateinit var solutions: List<CandidIdentifierDeclaration>

    override fun getText(): String = "Add import"
    override fun getFamilyName(): String = text

    override fun isAvailable(project: Project, editor: Editor?, file: PsiFile?): Boolean {
        this.solutions = StubIndex.getElements(
            CandidStubTypeIndex.Key,
            unresolved.text,
            project,
            GlobalSearchScope.projectScope(project),
            CandidIdentifierDeclaration::class.java
        ).toList()

        return this.solutions.isNotEmpty()
    }

    override fun invoke(project: Project, editor: Editor?, file: PsiFile?) {
        if (editor == null || file == null) return
        val candidFile = file as? CandidFile ?: return

        CommandProcessor.getInstance().runUndoTransparentAction {
            createAction(editor, candidFile).execute()
        }
    }

    private fun createAction(editor: Editor, file: CandidFile): CandidAddImportAction {
        return CandidAddImportAction(editor, file, solutions)
    }

    override fun showHint(editor: Editor): Boolean {
        val candidFile = unresolved.containingFile as? CandidFile ?: return false
        val action = createAction(editor, candidFile)
        HintManager.getInstance()
            .showQuestionHint(editor, hintText(), unresolved.startOffset, unresolved.endOffset, action)
        return true
    }

    private fun hintText(): String {
        val suggestion = if (solutions.isNotEmpty()) {
            unresolved.getRelativePath(solutions[0])
        } else ""

        return if (solutions.size == 1) {
            "Import from $suggestion"
        } else {
            "Import from $suggestion or other ${solutions.size - 1} files"
        }
    }
}