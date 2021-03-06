package com.github.alaanor.candid

import com.github.alaanor.candid.icon.CandidIcons
import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

class CandidFileType private constructor() : LanguageFileType(CandidLanguage.INSTANCE) {
    companion object {
        val INSTANCE = CandidFileType()
    }

    override fun getName(): String {
        return "Candid"
    }

    override fun getDescription(): String {
        return "Candid language file"
    }

    override fun getDefaultExtension(): String {
        return "did"
    }

    override fun getIcon(): Icon {
        return CandidIcons.FileType
    }
}