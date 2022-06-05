package com.github.alaanor.candid.inspection

import com.github.alaanor.candid.project.DfxJson
import com.github.alaanor.candid.psi.CandidMethodType
import com.intellij.codeInspection.*
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil

class CandidUnusedMethodInspection : LocalInspectionTool() {
    override fun checkFile(file: PsiFile, manager: InspectionManager, isOnTheFly: Boolean): Array<ProblemDescriptor>? {
        if (!DfxJson.isRustCanister(file.project, file.virtualFile))
            return null

        val problemsHolder = ProblemsHolder(manager, file, isOnTheFly)
        val methods = PsiTreeUtil.findChildrenOfType(file, CandidMethodType::class.java)

        methods.forEach { method ->
            if (method.reference?.resolve() != null)
                return@forEach

            problemsHolder.registerProblem(
                method.originalElement,
                "No matching rust method found for this candid method",
                ProblemHighlightType.LIKE_UNUSED_SYMBOL
            )
        }

        return problemsHolder.resultsArray
    }
}