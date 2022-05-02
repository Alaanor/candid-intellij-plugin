package com.github.alaanor.candid.psi

import com.github.alaanor.candid.CandidFileType
import com.github.alaanor.candid.CandidLanguage
import com.github.alaanor.candid.icon.CandidIcons
import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import javax.swing.Icon

class CandidFile (fileViewProvider: FileViewProvider) : PsiFileBase(fileViewProvider, CandidLanguage.INSTANCE) {
    override fun getFileType(): FileType {
        return CandidFileType.INSTANCE
    }

    override fun toString(): String {
        return "Candid File"
    }

    override fun getIcon(flags: Int): Icon {
        return CandidIcons.FileType
    }
}