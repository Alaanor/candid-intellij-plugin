package com.github.alaanor.candid.psi

import com.github.alaanor.candid.CandidFileType
import com.github.alaanor.candid.CandidLanguage
import com.github.alaanor.candid.CandidTypes
import com.github.alaanor.candid.icon.CandidIcons
import com.github.alaanor.candid.util.filePath
import com.github.alaanor.candid.util.getRelativePath
import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.elementType
import javax.swing.Icon

class CandidFile(fileViewProvider: FileViewProvider) : PsiFileBase(fileViewProvider, CandidLanguage.INSTANCE) {
    override fun getFileType(): FileType {
        return CandidFileType.INSTANCE
    }

    override fun toString(): String {
        return "Candid File"
    }

    override fun getIcon(flags: Int): Icon {
        return CandidIcons.FileType
    }

    fun addImportFor(declaration: CandidIdentifierDeclaration) {
        val requiredImportPath = this.getRelativePath(declaration)
        val imports = getImports()
        val exists = imports.find { requiredImportPath == it.importPathString() } != null

        if (exists || this.filePath() == declaration.filePath()) {
            return
        }

        val lastImport = imports.lastOrNull()?.let {
            if (it.nextSibling.elementType == CandidTypes.SEMICOLON)
                return@let it.nextSibling
            return@let it
        }

        val newImport = CandidElementFactory.createImportStatement(project, requiredImportPath)
        if (lastImport != null) {
            addRangeAfter(newImport.firstChild, newImport.lastChild, lastImport)
        } else {
            addRangeBefore(newImport.firstChild, newImport.lastChild, firstChild)
        }
    }


    private fun getImports(): Collection<CandidImportStatement> {
        return PsiTreeUtil.findChildrenOfType(this, CandidImportStatement::class.java)
    }
}