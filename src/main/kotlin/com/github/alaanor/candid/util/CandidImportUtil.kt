package com.github.alaanor.candid.util

import com.github.alaanor.candid.psi.CandidImportStatement
import com.github.alaanor.candid.psi.importPathString
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.containers.addIfNotNull

object CandidImportUtil {
    fun getAllImportedFileFor(psiFile: PsiFile, recursively: Boolean = true): List<PsiFile> {
        val targetFiles = mutableListOf<PsiFile>()
        PsiTreeUtil.findChildrenOfType(psiFile, CandidImportStatement::class.java).forEach { importStatement ->
            importStatement.importPathString()?.let { importPath ->
                targetFiles.addIfNotNull(resolveRelatively(psiFile, importPath))
            }
        }

        if (recursively) {
            targetFiles.addAll(
                targetFiles
                    .map { getAllImportedFileFor(it, true) }
                    .flatten()
            )
        }

        return targetFiles
    }

    fun resolveRelatively(psiFile: PsiFile, importPath: String): PsiFile? {
        val target = psiFile.virtualFile.findFileByRelativePath("../${importPath}")
        return target?.let { PsiManager.getInstance(psiFile.project).findFile(it) }
    }
}