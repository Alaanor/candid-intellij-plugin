package com.github.alaanor.candid

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType

import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.psi.TokenType

class CandidSyntaxHighlighter: SyntaxHighlighterBase() {
    companion object {
        private val DEFINITION = createTextAttributesKey("CANDID_TYPE", DefaultLanguageHighlighterColors.KEYWORD)
        private val STRING_LITERAL = createTextAttributesKey("CANDID_STRING_LITERAL", DefaultLanguageHighlighterColors.STRING)
        private val IDENTIFIER = createTextAttributesKey("CANDID_IDENTIFIER", DefaultLanguageHighlighterColors.IDENTIFIER)
        private val ESCAPE = createTextAttributesKey("CANDID_ESCAPE", DefaultLanguageHighlighterColors.VALID_STRING_ESCAPE)
        private val BAD_CHARACTER = createTextAttributesKey("CANDID_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER)

        val DEFINITION_KEYS = arrayOf(DEFINITION)
        val STRING_LITERAL_KEYS = arrayOf(STRING_LITERAL)
        val IDENTIFIER_KEYS = arrayOf(IDENTIFIER)
        val BAD_CHAR_KEYS = arrayOf(BAD_CHARACTER)
        val ESCAPE_KEYS= arrayOf(ESCAPE)
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
            CandidTypes.DOUBLE_QUOTE -> STRING_LITERAL_KEYS
            CandidTypes.UTF8ENC -> STRING_LITERAL_KEYS
            CandidTypes.ASCII -> STRING_LITERAL_KEYS
            CandidTypes.HEX_ESCAPE_SHORT -> ESCAPE_KEYS
            CandidTypes.HEX_ESCAPE_LONG -> ESCAPE_KEYS
            CandidTypes.ID -> IDENTIFIER_KEYS
            CandidTypes.ESCAPE -> ESCAPE_KEYS
            TokenType.BAD_CHARACTER -> BAD_CHAR_KEYS
            else -> EMPTY_KEYS
        }
    }
}