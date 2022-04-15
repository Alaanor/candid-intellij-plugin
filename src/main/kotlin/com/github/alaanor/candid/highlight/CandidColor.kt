package com.github.alaanor.candid.highlight

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.options.colors.AttributesDescriptor

enum class CandidColor(humanName: String, default: TextAttributesKey? = null) {
    KEYWORD("Keywords", DefaultLanguageHighlighterColors.KEYWORD),
    TYPE("Type", DefaultLanguageHighlighterColors.PREDEFINED_SYMBOL),
    STRING("String literal", DefaultLanguageHighlighterColors.STRING),
    IDENTIFIER("Identifier", DefaultLanguageHighlighterColors.FUNCTION_DECLARATION),
    SEMICOLON("Semicolon", DefaultLanguageHighlighterColors.SEMICOLON),
    ESCAPE("Escape", DefaultLanguageHighlighterColors.VALID_STRING_ESCAPE),
    LINE_COMMENT("Comments//Line comment", DefaultLanguageHighlighterColors.LINE_COMMENT),
    BLOCK_COMMENT("Comments//Block comment", DefaultLanguageHighlighterColors.BLOCK_COMMENT),
    BAD_CHARACTER("Bad character", HighlighterColors.BAD_CHARACTER),
    ;

    val textAttributesKey: TextAttributesKey = TextAttributesKey.createTextAttributesKey("com.github.alaanor.candid.$name", default)
    val attributesDescriptor: AttributesDescriptor = AttributesDescriptor(humanName, textAttributesKey)
}