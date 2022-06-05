package com.github.alaanor.candid.reference

import com.github.alaanor.candid.project.DfxJson
import com.github.alaanor.candid.psi.CandidMethodType
import com.github.alaanor.candid.psi.methodNameText
import com.github.alaanor.candid.util.CandidRustIcCdkUtil
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReferenceBase
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.PsiSearchHelper
import com.intellij.psi.stubs.StubIndex
import com.intellij.psi.util.PsiTreeUtil
import org.rust.lang.core.psi.RsFunction
import org.rust.lang.core.psi.RsMetaItem
import org.rust.lang.core.psi.ext.RsNamedElement
import org.rust.lang.core.stubs.index.RsNamedElementIndex

class CandidMethodReference(method: CandidMethodType, private var textRange: TextRange) :
    PsiReferenceBase<CandidMethodType>(method, textRange) {

    override fun getAbsoluteRange(): TextRange = textRange

    override fun resolve(): PsiElement? {
        val rustPackageScope = DfxJson.getRustCanisterScopeFromCandidFile(
            element.project,
            element.containingFile.virtualFile
        ) ?: return null

        val lookupName = element.methodNameText()
        return resolveByMethodName(rustPackageScope, lookupName)
            ?: resolveByMetaItem(element.project, rustPackageScope, lookupName)
    }

    private fun resolveByMethodName(rustPackageScope: GlobalSearchScope, lookupName: String): PsiElement? {
        val namedElements = StubIndex.getElements(
            RsNamedElementIndex.KEY,
            lookupName,
            element.project,
            rustPackageScope,
            RsNamedElement::class.java
        )

        val rsFunction = namedElements
            .mapNotNull {
                val rsFunction = it as? RsFunction ?: return@mapNotNull null
                if (CandidRustIcCdkUtil.getName(rsFunction) != element.methodNameText()) {
                    return@mapNotNull null
                }

                rsFunction
            }
            .firstOrNull()

        return rsFunction?.identifier
    }

    private fun resolveByMetaItem(
        project: Project,
        rustPackageScope: GlobalSearchScope,
        lookupName: String
    ): PsiElement? {
        var rsMetaItem: RsMetaItem? = null

        PsiSearchHelper.getInstance(project)
            .processAllFilesWithWordInLiterals(lookupName, rustPackageScope) processor@{ file ->
                if (!rustPackageScope.contains(file.virtualFile)) {
                    return@processor false
                }

                val possibleMetaItem = PsiTreeUtil.findChildrenOfType(file, RsMetaItem::class.java)
                    .filter { CandidRustIcCdkUtil.isIcCdkUpdateQuery(it) }
                    .firstOrNull { CandidRustIcCdkUtil.getName(it) == lookupName }
                    ?: return@processor false

                rsMetaItem = possibleMetaItem
                return@processor true
            }

        return rsMetaItem?.parent?.parent as? RsFunction ?: return null
    }

}