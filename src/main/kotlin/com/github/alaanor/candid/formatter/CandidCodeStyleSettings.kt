
package com.github.alaanor.candid.formatter

import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.codeStyle.CustomCodeStyleSettings

@Suppress("PropertyName")
class CandidCodeStyleSettings(settings: CodeStyleSettings) : CustomCodeStyleSettings("CandidCodeStyleSettings", settings) {
    @JvmField
    var SPACE_AFTER_RECORD = true

    @JvmField
    var SPACE_AFTER_VARIANT = true

    @JvmField
    var SPACE_AFTER_SERVICE = true

    @JvmField
    var SPACE_AFTER_FUNC = true

    @JvmField
    var NEW_LINE_FIELDS = true

    @JvmField
    var BLANK_LINE_AROUND_DEFINITION = 1

    @JvmField
    var BLANK_LINE_AROUND_IMPORT = 0
}