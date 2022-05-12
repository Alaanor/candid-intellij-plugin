package com.github.alaanor.candid.formatter

import com.github.alaanor.candid.CandidLanguage
import com.github.alaanor.candid.CandidTypes
import com.intellij.formatting.*
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.tree.TokenSet

class CandidFormattingModelBuilder : FormattingModelBuilder {
    companion object {
        val fields_tokens = TokenSet.create(
            CandidTypes.FIELD_TYPE_RECORD,
            CandidTypes.FIELD_TYPE_VARIANT,
            CandidTypes.METH_TYPE
        )

        val comment_tokens = TokenSet.create(
            CandidTypes.BLOCK_COMMENT,
            CandidTypes.LINE_COMMENT
        )

        val fields_and_semicolon = TokenSet.orSet(
            fields_tokens,
            TokenSet.create(CandidTypes.SEMICOLON)
        )
    }
    override fun createModel(formattingContext: FormattingContext): FormattingModel {
        val codeStyleSettings = formattingContext.codeStyleSettings
        return FormattingModelProvider.createFormattingModelForPsiFile(
            formattingContext.containingFile,
            CandidBlock(formattingContext.node, null, null, createSpaceBuilder(codeStyleSettings)),
            codeStyleSettings
        )
    }

    private fun createSpaceBuilder(codeStyleSettings: CodeStyleSettings): SpacingBuilder {
        val customSettings = codeStyleSettings.getCustomSettings(CandidCodeStyleSettings::class.java)
        val commonSettings = codeStyleSettings.getCommonSettings(CandidLanguage.INSTANCE)
        return SpacingBuilder(codeStyleSettings, CandidLanguage.INSTANCE)
            .before(CandidTypes.SEMICOLON).none()
            .around(CandidTypes.OP_EQ).spaceIf(commonSettings.SPACE_AROUND_ASSIGNMENT_OPERATORS)
            .after(CandidTypes.IMPORT).spaces(1)
            .after(CandidTypes.TYPE).spaces(1)
            .after(CandidTypes.COLON).spaceIf(commonSettings.SPACE_AFTER_COLON)
            .after(CandidTypes.COMMA).spaceIf(commonSettings.SPACE_AFTER_COMMA)
            .between(CandidTypes.LBRACE, fields_tokens).lineBreakInCodeIf(customSettings.NEW_LINE_FIELDS)
            .between(fields_and_semicolon, CandidTypes.RBRACE).lineBreakInCodeIf(customSettings.NEW_LINE_FIELDS)
            .before(fields_tokens).lineBreakInCodeIf(customSettings.NEW_LINE_FIELDS)
            .between(CandidTypes.LBRACE, CandidTypes.RBRACE).none()
            .after(CandidTypes.LBRACE).spaceIf(commonSettings.SPACE_WITHIN_BRACES)
            .before(CandidTypes.RBRACE).spaceIf(commonSettings.SPACE_WITHIN_BRACES)
            .between(CandidTypes.LPAREN, CandidTypes.RPAREN).none()
            .after(CandidTypes.LPAREN).spaceIf(commonSettings.SPACE_WITHIN_PARENTHESES)
            .before(CandidTypes.RPAREN).spaceIf(commonSettings.SPACE_WITHIN_PARENTHESES)
            .after(CandidTypes.RECORD).spaceIf(customSettings.SPACE_AFTER_RECORD)
            .after(CandidTypes.VARIANT).spaceIf(customSettings.SPACE_AFTER_VARIANT)
            .after(CandidTypes.SERVICE).spaceIf(customSettings.SPACE_AFTER_SERVICE)
            .after(CandidTypes.FUNC).spaceIf(customSettings.SPACE_AFTER_FUNC)
            .between(comment_tokens, CandidTypes.DEFINITION).blankLines(0)
            .around(CandidTypes.DEFINITION).blankLines(customSettings.BLANK_LINE_AROUND_DEFINITION)
            .between(comment_tokens, CandidTypes.IMPORT_STATEMENT).blankLines(0)
            .around(CandidTypes.IMPORT_STATEMENT).blankLines(customSettings.BLANK_LINE_AROUND_IMPORT)
    }
}