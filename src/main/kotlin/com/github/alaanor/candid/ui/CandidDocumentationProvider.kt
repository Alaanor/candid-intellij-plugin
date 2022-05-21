package com.github.alaanor.candid.ui

import com.github.alaanor.candid.CandidTypes
import com.github.alaanor.candid.psi.CandidElementFactory
import com.intellij.lang.documentation.DocumentationMarkup
import com.intellij.lang.documentation.DocumentationProvider
import com.intellij.openapi.editor.colors.EditorColorsManager
import com.intellij.openapi.editor.richcopy.HtmlSyntaxInfoUtil
import com.intellij.psi.PsiElement
import com.intellij.psi.TokenType
import com.intellij.psi.presentation.java.SymbolPresentationUtil
import com.intellij.psi.util.elementType

class CandidDocumentationProvider : DocumentationProvider {

    override fun generateDoc(element: PsiElement?, originalElement: PsiElement?): String? {
        val parent = originalElement?.parent ?: return null
        return when (parent.elementType) {
            CandidTypes.IDENTIFIER_REFERENCE -> candidTypeDoc(parent)
            else -> null
        }
    }

    private fun candidTypeDoc(element: PsiElement): String {
        val declaration = element.reference?.resolve()
            ?: return "Failed to resolve - no documentation available"

        val project = element.project
        val text = declaration.parent.text

        val commentDocumentation = getCommentDocumentation(declaration)
        val fileLink = SymbolPresentationUtil.getFilePathPresentation(declaration.containingFile)
        val definitionCode = HtmlSyntaxInfoUtil.getHtmlContent(
            CandidElementFactory.createDummyFile(project, text),
            text, null,
            EditorColorsManager.getInstance().globalScheme,
            0, text.length
        ).toString()

        return buildString {
            if (commentDocumentation != null) {
                append(DocumentationMarkup.SECTIONS_START)
                append(commentDocumentation)
                append(DocumentationMarkup.SECTIONS_END)
            }
            append(DocumentationMarkup.DEFINITION_START)
            append(definitionCode)
            append(DocumentationMarkup.DEFINITION_END)
            append(DocumentationMarkup.SECTIONS_START)
            append(fileLink)
            append(DocumentationMarkup.SECTIONS_END)
        }
    }

    private fun getCommentDocumentation(declaration: PsiElement): String? {
        val potentialWhitespace = declaration.parent.prevSibling ?: return null
        if (potentialWhitespace.elementType != TokenType.WHITE_SPACE || potentialWhitespace.text.count { it == '\n' } != 1) {
            return null
        }

        val potentialDocumentation = potentialWhitespace.prevSibling ?: return null
        return when (potentialDocumentation.elementType) {
            CandidTypes.LINE_COMMENT -> {
                return potentialDocumentation.text
                    .removePrefix("//")
                    .trim()
            }

            CandidTypes.BLOCK_COMMENT -> {
                return potentialDocumentation.text
                    .removeSurrounding("/*", "*/")
                    .lines()
                    .joinToString("<br>") {
                        it.trim().removePrefix("*").trim()
                    }
            }
            else -> null
        }
    }
}