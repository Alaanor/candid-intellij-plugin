package com.github.alaanor.candid.icon

import com.github.alaanor.candid.CandidFileType
import com.intellij.ide.IconProvider
import com.intellij.psi.PsiElement
import javax.swing.Icon

class CandidIconProvider : IconProvider() {
    override fun getIcon(element: PsiElement, flags: Int): Icon? {
        if (element.containingFile?.fileType !is CandidFileType) {
            return null
        }

        return CandidIcons.FileType
    }
}