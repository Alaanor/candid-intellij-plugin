package com.github.alaanor.candid.formatter

import com.github.alaanor.candid.CandidLanguage
import com.intellij.application.options.IndentOptionsEditor
import com.intellij.application.options.SmartIndentOptionsEditor
import com.intellij.lang.Language
import com.intellij.psi.codeStyle.*

class CandidLanguageCodeStyleSettingsProvider : LanguageCodeStyleSettingsProvider() {
    override fun getLanguage(): Language = CandidLanguage.INSTANCE
    override fun getIndentOptionsEditor(): IndentOptionsEditor = SmartIndentOptionsEditor(this)

    override fun customizeSettings(consumer: CodeStyleSettingsCustomizable, settingsType: SettingsType) {
        when (settingsType) {
            SettingsType.SPACING_SETTINGS -> {
                consumer.showStandardOptions(CodeStyleSettingsCustomizable.SpacingOption.SPACE_AROUND_ASSIGNMENT_OPERATORS.name)
                consumer.renameStandardOption(CodeStyleSettingsCustomizable.SpacingOption.SPACE_AROUND_ASSIGNMENT_OPERATORS.name, "Assigment operators =")
            }

            SettingsType.INDENT_SETTINGS -> {
                consumer.showStandardOptions(
                    CodeStyleSettingsCustomizable.IndentOption.USE_TAB_CHARACTER.name,
                    CodeStyleSettingsCustomizable.IndentOption.INDENT_SIZE.name,
                    CodeStyleSettingsCustomizable.IndentOption.CONTINUATION_INDENT_SIZE.name
                )
            }

            else -> {}
        }
    }

    override fun customizeDefaults(
        commonSettings: CommonCodeStyleSettings,
        indentOptions: CommonCodeStyleSettings.IndentOptions
    ) {
        indentOptions.CONTINUATION_INDENT_SIZE = 4
    }

    override fun getCodeSample(settingsType: SettingsType): String? {
        return """
import "foo/bar.did";

type Name = text;

type Entry = record {
    person: Name;
    "age": nat;
};

service: {
    insert: (Name, nat) -> ();
    lookup: (Name) -> (opt Entry) query;
} 
        """.trim()
    }

}