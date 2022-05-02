package com.github.alaanor.candid.inspection

import com.github.alaanor.candid.psi.CandidIdentifierReference
import com.intellij.codeInspection.*
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil

class CandidUnresolvedReferenceInspection : LocalInspectionTool() {

    override fun checkFile(file: PsiFile, manager: InspectionManager, isOnTheFly: Boolean): Array<ProblemDescriptor>? {
        val problemsHolder = ProblemsHolder(manager, file, isOnTheFly)

        PsiTreeUtil.findChildrenOfType(file, CandidIdentifierReference::class.java).forEach { reference ->
            if (reference.reference?.resolve() != null) {
                return@forEach
            }

            problemsHolder.registerProblem(
                reference,
                "Unresolved reference",
                ProblemHighlightType.ERROR
            )
        }

        return problemsHolder.resultsArray
    }

}