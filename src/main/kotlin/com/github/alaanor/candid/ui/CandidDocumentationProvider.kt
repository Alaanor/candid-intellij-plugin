package com.github.alaanor.candid.ui

import com.github.alaanor.candid.CandidTypes
import com.github.alaanor.candid.psi.CandidElementFactory
import com.intellij.lang.documentation.DocumentationProvider
import com.intellij.openapi.editor.colors.EditorColorsManager
import com.intellij.openapi.editor.richcopy.HtmlSyntaxInfoUtil
import com.intellij.psi.PsiElement
import com.intellij.psi.util.elementType

class CandidDocumentationProvider : DocumentationProvider {

    override fun generateDoc(element: PsiElement?, originalElement: PsiElement?): String? {
        val parent = originalElement?.parent ?: return null
        return when(parent.elementType) {
            CandidTypes.IDENTIFIER_REFERENCE -> candidTypeDoc(parent)
            else -> null
        }
    }

    private fun candidTypeDoc(element: PsiElement): String {
        val declaration = element.reference?.resolve()
            ?: return "Failed to resolve - no documentation available"

        val project = element.project
        val text = declaration.parent.text
        return HtmlSyntaxInfoUtil.getHtmlContent(
            CandidElementFactory.createDummyFile(project, text),
            text, null,
            EditorColorsManager.getInstance().globalScheme,
            0, text.length
        ).toString()
    }
}