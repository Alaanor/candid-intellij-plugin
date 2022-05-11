
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
}