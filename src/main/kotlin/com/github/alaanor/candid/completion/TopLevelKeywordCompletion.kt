package com.github.alaanor.candid.completion

import com.github.alaanor.candid.psi.CandidFile
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.patterns.ElementPattern
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiErrorElement

class TopLevelKeywordCompletion : CandidBasicCompletion() {
    override val elementPattern: ElementPattern<out PsiElement> = PlatformPatterns.psiElement()
        .andNot(PlatformPatterns.psiElement().beforeLeaf(";"))
        .withParent(PsiErrorElement::class.java)
        .withSuperParent(2, CandidFile::class.java)

    override val completionProvider: CompletionProvider<CompletionParameters> = KeywordsProvider(
        listOf("type", "service"),
        insertHandler = SpaceAfterInsertHandler
    )
}