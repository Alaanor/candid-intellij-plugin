package com.github.alaanor.candid

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType

import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey

class CandidSyntaxHighlighter: SyntaxHighlighterBase() {
    companion object {
        private val DEFINITION = createTextAttributesKey("CANDID_TYPE", DefaultLanguageHighlighterColors.KEYWORD)

        val DEFINITION_KEYS = arrayOf(DEFINITION)
        val EMPTY_KEYS: Array<TextAttributesKey> = arrayOf()
    }

    override fun getHighlightingLexer(): Lexer {
        return CandidLexerAdapter()
    }

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
        return when (tokenType) {
            CandidTypes.TYPE -> DEFINITION_KEYS
            CandidTypes.RECORD -> DEFINITION_KEYS
            CandidTypes.VARIANT -> DEFINITION_KEYS
            CandidTypes.SERVICE -> DEFINITION_KEYS
            else -> EMPTY_KEYS
        }
    }
}