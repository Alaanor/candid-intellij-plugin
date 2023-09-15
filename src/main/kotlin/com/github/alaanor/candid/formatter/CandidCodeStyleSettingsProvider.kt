package com.github.alaanor.candid.formatter

import com.github.alaanor.candid.CandidLanguage
import com.intellij.application.options.CodeStyleAbstractConfigurable
import com.intellij.application.options.CodeStyleAbstractPanel
import com.intellij.application.options.TabbedLanguageCodeStylePanel
import com.intellij.psi.codeStyle.CodeStyleConfigurable
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.codeStyle.CodeStyleSettingsProvider
import com.intellij.psi.codeStyle.CustomCodeStyleSettings

class CandidCodeStyleSettingsProvider : CodeStyleSettingsProvider() {
    override fun getConfigurableDisplayName(): String = "Candid"

    override fun createCustomSettings(settings: CodeStyleSettings): CustomCodeStyleSettings {
        return CandidCodeStyleSettings(settings)
    }

    override fun createConfigurable(
        settings: CodeStyleSettings,
        modelSettings: CodeStyleSettings
    ): CodeStyleConfigurable {
        return CandidCodeStyleConfigurable(settings, modelSettings, configurableDisplayName)
    }

    class CandidCodeStyleConfigurable(
        settings: CodeStyleSettings,
        cloneSettings: CodeStyleSettings,
        displayName: String
    ) : CodeStyleAbstractConfigurable(settings, cloneSettings, displayName) {
        override fun createPanel(settings: CodeStyleSettings): CodeStyleAbstractPanel {
            return CandidCodeStyleMainPanel(currentSettings, settings)
        }
    }

    class CandidCodeStyleMainPanel(currentSettings: CodeStyleSettings, settings: CodeStyleSettings) :
        TabbedLanguageCodeStylePanel(CandidLanguage.INSTANCE, currentSettings, settings)
}