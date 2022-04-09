package com.github.alaanor.candid.completion

import com.github.alaanor.candid.psi.CandidFile
import com.intellij.codeInsight.completion.InsertHandler
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.patterns.ElementPattern
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiErrorElement

class TopLevelKeywordCompletion : CandidBasicCompletion() {
    override val elementPattern: ElementPattern<out PsiElement> = PlatformPatterns.psiElement()
        .andNot(PlatformPatterns.psiElement().beforeLeaf(";"))
        .withParent(PsiErrorElement::class.java)
        .withSuperParent(2, CandidFile::class.java)

    override val tokenList: List<String> = listOf("type", "service")

    override fun keywordInsertHandle(keyword: String): InsertHandler<LookupElement> {
        return SpaceAfterInsertHandler
    }
}