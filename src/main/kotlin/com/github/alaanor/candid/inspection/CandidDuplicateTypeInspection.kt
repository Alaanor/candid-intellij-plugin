package com.github.alaanor.candid.inspection

import com.github.alaanor.candid.psi.CandidIdentifierDeclaration
import com.github.alaanor.candid.psi.stub.index.CandidStubTypeIndex
import com.github.alaanor.candid.util.CandidImportUtil
import com.github.alaanor.candid.util.projectFilePath
import com.intellij.codeInspection.*
import com.intellij.psi.PsiFile
import com.intellij.psi.stubs.StubIndex
import com.intellij.psi.util.PsiTreeUtil

class CandidDuplicateTypeInspection : LocalInspectionTool() {
    override fun checkFile(file: PsiFile, manager: InspectionManager, isOnTheFly: Boolean): Array<ProblemDescriptor>? {
        val problemsHolder = ProblemsHolder(manager, file, isOnTheFly)

        PsiTreeUtil.findChildrenOfType(file, CandidIdentifierDeclaration::class.java).forEach { declaration ->
            val sameNameDeclarations = StubIndex.getElements(
                CandidStubTypeIndex.Key,
                declaration.text,
                file.project,
                CandidImportUtil.getImportedSearchScope(file),
                CandidIdentifierDeclaration::class.java
            )

            if (sameNameDeclarations.count() < 2) {
                return@forEach
            }

            val others = sameNameDeclarations.filter { declaration != it }
            problemsHolder.registerProblem(
                declaration,
                "Type with the same name already declared in ${others[0].projectFilePath()}",
                ProblemHighlightType.GENERIC_ERROR
            )
        }

        return problemsHolder.resultsArray
    }
}