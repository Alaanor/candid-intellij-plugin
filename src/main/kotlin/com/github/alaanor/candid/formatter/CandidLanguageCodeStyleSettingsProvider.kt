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
                consumer.showStandardOptions(
                    CodeStyleSettingsCustomizable.SpacingOption.SPACE_AROUND_ASSIGNMENT_OPERATORS.name,
                    CodeStyleSettingsCustomizable.SpacingOption.SPACE_WITHIN_BRACES.name,
                    CodeStyleSettingsCustomizable.SpacingOption.SPACE_WITHIN_PARENTHESES.name,
                    CodeStyleSettingsCustomizable.SpacingOption.SPACE_AFTER_COLON.name,
                    CodeStyleSettingsCustomizable.SpacingOption.SPACE_AFTER_COMMA.name
                )
                consumer.renameStandardOption(
                    CodeStyleSettingsCustomizable.SpacingOption.SPACE_AROUND_ASSIGNMENT_OPERATORS.name,
                    "Assigment operators ="
                )
                consumer.moveStandardOption(
                    CodeStyleSettingsCustomizable.SpacingOption.SPACE_AFTER_COLON.name,
                    "Other"
                )

                consumer.showCustomOption(
                    CandidCodeStyleSettings::class.java,
                    CandidCodeStyleSettings::SPACE_AFTER_RECORD.name,
                    "After record",
                    "Other"
                )

                consumer.showCustomOption(
                    CandidCodeStyleSettings::class.java,
                    CandidCodeStyleSettings::SPACE_AFTER_VARIANT.name,
                    "After variant",
                    "Other"
                )

                consumer.showCustomOption(
                    CandidCodeStyleSettings::class.java,
                    CandidCodeStyleSettings::SPACE_AFTER_SERVICE.name,
                    "After service",
                    "Other"
                )

                consumer.showCustomOption(
                    CandidCodeStyleSettings::class.java,
                    CandidCodeStyleSettings::SPACE_AFTER_FUNC.name,
                    "After func",
                    "Other"
                )
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
        commonSettings.SPACE_WITHIN_BRACES = true
    }

    override fun getCodeSample(settingsType: SettingsType): String? {
        return """
import "foo/bar.did";

type Name = text;

type Entry = record {
    person: Name; // some comment
    get_details: func () -> (EntryDetails) query;
};

/*
    Multi line comment
*/
type EntryDetails = record {
    "age": nat;
    description: opt text;
};

type LookupResult = variant { Ok: Entry; Err; }

service: {
    insert: (Name, nat) -> ();
    lookup: (Name) -> (LookupResult) query;
} 
        """.trim()
    }

}