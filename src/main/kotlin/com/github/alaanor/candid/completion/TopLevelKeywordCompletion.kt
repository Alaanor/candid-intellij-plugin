package com.github.alaanor.candid.completion

import com.github.alaanor.candid.psi.CandidActor
import com.github.alaanor.candid.psi.CandidFile
import com.intellij.codeInsight.completion.InsertHandler
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.patterns.ElementPattern
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiErrorElement

class TopLevelKeywordCompletion : CandidBasicCompletion() {
    override val elementPattern: ElementPattern<out PsiElement> = psiElement()
        .andNot(psiElement().beforeLeaf(";"))
        .withParent(PsiErrorElement::class.java)
        .withSuperParent(
            2,
            psiElement(CandidFile::class.java).andNot(
                psiElement().withChild(psiElement(CandidActor::class.java))
            )
        )

    override val tokenList: List<String> = listOf("type", "service", "import")

    override fun keywordInsertHandle(keyword: String): InsertHandler<LookupElement> {
        return when (keyword) {
            "import" -> QuoteSemicolonAfterInsertHandler
            else -> SpaceAfterInsertHandler
        }
    }
}