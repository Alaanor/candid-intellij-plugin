package com.github.alaanor.candid.inspection

import com.github.alaanor.candid.quickfix.CandidRemoveUnusedImportQuickFix
import com.github.alaanor.candid.util.CandidImportUtil
import com.intellij.codeInspection.*
import com.intellij.psi.PsiFile

class CandidUnusedImportInspection: LocalInspectionTool() {
    override fun checkFile(file: PsiFile, manager: InspectionManager, isOnTheFly: Boolean): Array<ProblemDescriptor>? {
        val problemsHolder = ProblemsHolder(manager, file, isOnTheFly)
        val unusedImports = CandidImportUtil.getAllUnusedImportFor(file)

        unusedImports.forEach { unusedImport ->
            problemsHolder.registerProblem(
                unusedImport.originalElement,
                "Unused import",
                ProblemHighlightType.LIKE_UNUSED_SYMBOL,
                CandidRemoveUnusedImportQuickFix(unusedImport)
            )
        }

        return problemsHolder.resultsArray
    }
}