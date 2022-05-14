package com.github.alaanor.candid.util

import com.github.alaanor.candid.psi.CandidImportStatement
import com.github.alaanor.candid.psi.importPathString
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.psi.util.PsiTreeUtil

object CandidImportUtil {

    fun getAllImportedFileFor(psiFile: PsiFile): List<PsiFile> {
        return getAllImportedFileFor(psiFile, mutableSetOf())
    }

    private fun getAllImportedFileFor(psiFile: PsiFile, fileSet: MutableSet<PsiFile>): List<PsiFile> {
        PsiTreeUtil.findChildrenOfType(psiFile, CandidImportStatement::class.java).forEach { importStatement ->
            val importPath = importStatement.importPathString() ?: return@forEach
            val file = resolveRelatively(psiFile, importPath) ?: return@forEach

            if (!fileSet.contains(file)) {
                fileSet.add(file)
                getAllImportedFileFor(file, fileSet)
            }
        }

        return fileSet.toList()
    }

    fun resolveRelatively(psiFile: PsiFile, importPath: String): PsiFile? {
        val target = psiFile.virtualFile.findFileByRelativePath("../${importPath}")
        return target?.let { PsiManager.getInstance(psiFile.project).findFile(it) }
    }
}