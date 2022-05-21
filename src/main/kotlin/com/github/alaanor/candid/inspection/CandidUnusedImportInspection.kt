package com.github.alaanor.candid.inspection

import com.github.alaanor.candid.quickfix.CandidRemoveImportQuickFix
import com.github.alaanor.candid.util.CandidImportUtil
import com.github.alaanor.candid.util.projectFilePath
import com.intellij.codeInspection.*
import com.intellij.psi.PsiFile

class CandidUnusedImportInspection : LocalInspectionTool() {
    override fun checkFile(file: PsiFile, manager: InspectionManager, isOnTheFly: Boolean): Array<ProblemDescriptor>? {
        val problemsHolder = ProblemsHolder(manager, file, isOnTheFly)
        val unusedImports = CandidImportUtil.getAllUnusedImportFor(file)
        val currentFilePath = file.projectFilePath()

        unusedImports.forEach { unusedImport ->
            if (unusedImport.projectFilePath() == currentFilePath) {
                return@forEach
            }

            problemsHolder.registerProblem(
                unusedImport.originalElement,
                "Unused import",
                ProblemHighlightType.LIKE_UNUSED_SYMBOL,
                CandidRemoveImportQuickFix(unusedImport, CandidRemoveImportQuickFix.Type.Unused)
            )
        }

        return problemsHolder.resultsArray
    }
}