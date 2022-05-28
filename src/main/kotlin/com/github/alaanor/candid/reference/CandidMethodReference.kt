package com.github.alaanor.candid.reference

import com.github.alaanor.candid.psi.CandidMethodType
import com.github.alaanor.candid.psi.methodNameText
import com.github.alaanor.candid.util.CandidRustIcCdkUtil
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReferenceBase
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.StubIndex
import org.rust.lang.core.psi.RsFunction
import org.rust.lang.core.psi.ext.RsNamedElement
import org.rust.lang.core.stubs.index.RsNamedElementIndex

class CandidMethodReference(method: CandidMethodType, private var textRange: TextRange) :
    PsiReferenceBase<CandidMethodType>(method, textRange) {

    override fun getAbsoluteRange(): TextRange = textRange

    override fun resolve(): PsiElement? {
        val namedElements = StubIndex.getElements(
            RsNamedElementIndex.KEY,
            element.methodNameText(),
            element.project,
            GlobalSearchScope.projectScope(element.project),
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
}