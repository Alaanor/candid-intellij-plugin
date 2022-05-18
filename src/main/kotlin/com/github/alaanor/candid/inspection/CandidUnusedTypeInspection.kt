package com.github.alaanor.candid.inspection

import com.github.alaanor.candid.psi.CandidIdentifierDeclaration
import com.github.alaanor.candid.psi.CandidIdentifierReference
import com.github.alaanor.candid.psi.impl.CandidActorImpl
import com.github.alaanor.candid.quickfix.CandidRemoveUnusedTypeQuickFix
import com.intellij.codeInspection.*
import com.intellij.psi.PsiFile
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.searches.ReferencesSearch
import com.intellij.psi.util.PsiTreeUtil

class CandidUnusedTypeInspection : LocalInspectionTool() {
    override fun checkFile(file: PsiFile, manager: InspectionManager, isOnTheFly: Boolean): Array<ProblemDescriptor>? {
        val problemsHolder = ProblemsHolder(manager, file, isOnTheFly)
        val declarations = PsiTreeUtil.findChildrenOfType(file, CandidIdentifierDeclaration::class.java)
            .filter { it.parent !is CandidActorImpl }
        val references = PsiTreeUtil.findChildrenOfType(file, CandidIdentifierReference::class.java)

        declarations.forEach { declaration ->
            // local first
            var found = references.find { declaration.text == it.text } != null

            // then global
            if (!found) {
                val occurrences = ReferencesSearch.search(declaration, GlobalSearchScope.projectScope(file.project))

                @Suppress("ReplaceSizeCheckWithIsNotEmpty")
                found = occurrences.count() > 0
            }

            if (!found) {
                problemsHolder.registerProblem(
                    declaration.originalElement,
                    "Unused type",
                    ProblemHighlightType.LIKE_UNUSED_SYMBOL,
                    CandidRemoveUnusedTypeQuickFix(declaration)
                )
            }
        }

        return problemsHolder.resultsArray
    }
}