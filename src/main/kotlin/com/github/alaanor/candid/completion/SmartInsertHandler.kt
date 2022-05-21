package com.github.alaanor.candid.completion

import com.intellij.codeInsight.completion.InsertHandler
import com.intellij.codeInsight.completion.InsertionContext
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.EditorModificationUtil
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiDocumentManager
import kotlin.math.min

val BraceAfterInsertHandler = SmartInsertHandler(" {}", -1, '{')
val FuncParenAfterInsertHandler = SmartInsertHandler(" () -> ()", -7, '(')
val QuoteSemicolonAfterInsertHandler = SmartInsertHandler(" \"\";", -2, '"')
val SpaceAfterInsertHandler = SmartInsertHandler(" ")
val EmptyInsertHandler = SmartInsertHandler("")

class SmartInsertHandler(
    private val value: String,
    private val offset: Int = 0,
    private val ignoreIfNextIs: Char? = null
) : InsertHandler<LookupElement> {

    override fun handleInsert(context: InsertionContext, item: LookupElement) {
        val editor = context.editor
        val project = editor.project ?: return
        val model = editor.caretModel

        if (shouldIgnore(editor)) {
            model.moveToOffset(getExistingCharPosition(editor) + 1)
        } else {
            model.moveToOffset(model.offset)
            EditorModificationUtil.insertStringAtCaret(editor, value)
            PsiDocumentManager.getInstance(project).commitDocument(editor.document)
            model.moveToOffset(model.offset + offset)
        }
    }

    private fun shouldIgnore(editor: Editor): Boolean {
        if (ignoreIfNextIs == null)
            return false

        val startOffset = editor.caretModel.offset
        val endOffset = min(startOffset + 32, editor.document.textLength) // don't look further than 32 char
        val tailText = editor.document.getText(TextRange.create(startOffset, endOffset)).trimStart()

        if (tailText.isEmpty())
            return false

        return tailText.first() == ignoreIfNextIs
    }

    private fun getExistingCharPosition(editor: Editor): Int {
        if (ignoreIfNextIs == null)
            return 0

        return editor.document.text.indexOf(ignoreIfNextIs, editor.caretModel.offset)
    }
}