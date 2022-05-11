package com.github.alaanor.candid.formatter

import com.github.alaanor.candid.CandidLanguage
import com.github.alaanor.candid.CandidTypes
import com.intellij.formatting.*
import com.intellij.psi.codeStyle.CodeStyleSettings

class CandidFormattingModelBuilder : FormattingModelBuilder {
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
            .after(CandidTypes.LBRACE).spaceIf(commonSettings.SPACE_WITHIN_BRACES)
            .before(CandidTypes.RBRACE).spaceIf(commonSettings.SPACE_WITHIN_BRACES)
            .between(CandidTypes.LPAREN, CandidTypes.RPAREN).none()
            .after(CandidTypes.LPAREN).spaceIf(commonSettings.SPACE_WITHIN_PARENTHESES)
            .before(CandidTypes.RPAREN).spaceIf(commonSettings.SPACE_WITHIN_PARENTHESES)
            .after(CandidTypes.RECORD).spaceIf(customSettings.SPACE_AFTER_RECORD)
            .after(CandidTypes.VARIANT).spaceIf(customSettings.SPACE_AFTER_VARIANT)
            .after(CandidTypes.SERVICE).spaceIf(customSettings.SPACE_AFTER_SERVICE)
            .after(CandidTypes.FUNC).spaceIf(customSettings.SPACE_AFTER_FUNC)
    }
}