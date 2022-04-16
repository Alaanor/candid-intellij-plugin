package com.github.alaanor.candid.highlight

import com.github.alaanor.candid.CandidLexerAdapter
import com.github.alaanor.candid.CandidTypes
import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType

import gnu.trove.THashMap

class CandidSyntaxHighlighter : SyntaxHighlighterBase() {

    override fun getHighlightingLexer(): Lexer {
        return CandidLexerAdapter()
    }

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> =
        pack(tokenMap[tokenType]?.textAttributesKey)

    private val tokenMap: Map<IElementType, CandidColor> = THashMap<IElementType, CandidColor>().apply {
        put(CandidTypes.DOUBLE_QUOTE, CandidColor.STRING)
        put(CandidTypes.UTF8ENC, CandidColor.STRING)
        put(CandidTypes.ASCII, CandidColor.STRING)

        put(CandidTypes.ESCAPE, CandidColor.ESCAPE)
        put(CandidTypes.HEX_ESCAPE_SHORT, CandidColor.ESCAPE)
        put(CandidTypes.HEX_ESCAPE_LONG, CandidColor.ESCAPE)

        put(CandidTypes.NAT, CandidColor.KEYWORD)
        put(CandidTypes.NAT8, CandidColor.KEYWORD)
        put(CandidTypes.NAT16, CandidColor.KEYWORD)
        put(CandidTypes.NAT32, CandidColor.KEYWORD)
        put(CandidTypes.NAT64, CandidColor.KEYWORD)
        put(CandidTypes.INT, CandidColor.KEYWORD)
        put(CandidTypes.INT8, CandidColor.KEYWORD)
        put(CandidTypes.INT16, CandidColor.KEYWORD)
        put(CandidTypes.INT32, CandidColor.KEYWORD)
        put(CandidTypes.INT64, CandidColor.KEYWORD)
        put(CandidTypes.FLOAT32, CandidColor.KEYWORD)
        put(CandidTypes.FLOAT64, CandidColor.KEYWORD)
        put(CandidTypes.BOOL, CandidColor.KEYWORD)
        put(CandidTypes.TEXT, CandidColor.KEYWORD)
        put(CandidTypes.NULL, CandidColor.KEYWORD)
        put(CandidTypes.RESERVED, CandidColor.KEYWORD)
        put(CandidTypes.EMPTY, CandidColor.KEYWORD)
        put(CandidTypes.PRINCIPAL, CandidColor.KEYWORD)
        put(CandidTypes.TYPE, CandidColor.KEYWORD)
        put(CandidTypes.OPT, CandidColor.KEYWORD)
        put(CandidTypes.RECORD, CandidColor.KEYWORD)
        put(CandidTypes.VARIANT, CandidColor.KEYWORD)
        put(CandidTypes.SERVICE, CandidColor.KEYWORD)
        put(CandidTypes.FUNC, CandidColor.KEYWORD)
        put(CandidTypes.VEC, CandidColor.KEYWORD)
        put(CandidTypes.BLOB, CandidColor.KEYWORD)
        put(CandidTypes.IMPORT, CandidColor.KEYWORD)

        put(CandidTypes.SEMICOLON, CandidColor.SEMICOLON)
        put(CandidTypes.LINE_COMMENT, CandidColor.LINE_COMMENT)
        put(CandidTypes.BLOCK_COMMENT, CandidColor.BLOCK_COMMENT)

        put(TokenType.BAD_CHARACTER, CandidColor.BAD_CHARACTER)
    }
}