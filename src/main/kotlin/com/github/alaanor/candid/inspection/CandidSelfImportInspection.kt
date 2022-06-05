package com.github.alaanor.candid.inspection

import com.github.alaanor.candid.psi.CandidImportStatement
import com.github.alaanor.candid.psi.getTextRangeWithoutQuote
import com.github.alaanor.candid.psi.importedPsiFile
import com.github.alaanor.candid.quickfix.CandidRemoveImportQuickFix
import com.github.alaanor.candid.util.projectFilePath
import com.intellij.codeInspection.*
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil

class CandidSelfImportInspection : LocalInspectionTool() {

    override fun checkFile(file: PsiFile, manager: InspectionManager, isOnTheFly: Boolean): Array<ProblemDescriptor>? {
        val problemsHolder = ProblemsHolder(manager, file, isOnTheFly)
        val currentFilePath = file.projectFilePath()

        PsiTreeUtil.findChildrenOfType(file, CandidImportStatement::class.java).forEach { import ->
            val importedPath = import.importedPsiFile()?.projectFilePath() ?: return@forEach

            if (importedPath == currentFilePath) {
                problemsHolder.registerProblem(
                    import,
                    "Cannot self import",
                    ProblemHighlightType.GENERIC_ERROR,
                    import.stringLiteral?.getTextRangeWithoutQuote() ?: import.textRange,
                    CandidRemoveImportQuickFix(import, CandidRemoveImportQuickFix.Type.Invalid)
                )
            }
        }

        return problemsHolder.resultsArray
    }
}