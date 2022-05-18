package com.github.alaanor.candid.formatter

import com.github.alaanor.candid.CandidTypes
import com.github.alaanor.candid.psi.*
import com.github.alaanor.candid.util.CandidImportUtil
import com.intellij.lang.ImportOptimizer
import com.intellij.psi.PsiFile
import com.intellij.psi.util.elementType

class CandidImportOptimizer : ImportOptimizer {
    override fun supports(file: PsiFile): Boolean = file is CandidFile

    override fun processFile(file: PsiFile): Runnable {
        return Runnable {
            val unusedFiles = CandidImportUtil.getAllUnusedImportFor(file)
            unusedFiles.forEach { importStatement ->
                if (importStatement.nextSibling.elementType == CandidTypes.SEMICOLON) {
                    importStatement.nextSibling.delete()
                }
                importStatement.delete()
            }
        }
    }
}