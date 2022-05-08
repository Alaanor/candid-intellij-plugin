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
        val commonSettings = codeStyleSettings.getCommonSettings(CandidLanguage.INSTANCE)
        return SpacingBuilder(codeStyleSettings, CandidLanguage.INSTANCE)
            .before(CandidTypes.SEMICOLON).none()
            .around(CandidTypes.OP_EQ).spaceIf(commonSettings.SPACE_AROUND_ASSIGNMENT_OPERATORS)
            .after(CandidTypes.IMPORT).spaces(1)
            .after(CandidTypes.TYPE).spaces(1)
            .after(CandidTypes.RECORD).spaces(1)
    }
}