package com.github.alaanor.candid.completion

import com.github.alaanor.candid.psi.CandidActor
import com.github.alaanor.candid.psi.CandidMethType
import com.intellij.codeInsight.completion.InsertHandler
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.patterns.ElementPattern
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.psi.PsiElement
import com.intellij.psi.TokenType

class MethodAnnotationCompletion : CandidBasicCompletion() {
    override val elementPattern: ElementPattern<out PsiElement> = psiElement()
        .afterSiblingSkipping(
            psiElement(TokenType.WHITE_SPACE),
            psiElement(CandidActor::class.java)
                .withLastChild(psiElement(CandidMethType::class.java))
        )

    override val tokenList: List<String> = listOf("query", "oneway")
    override fun keywordInsertHandle(keyword: String): InsertHandler<LookupElement> = EmptyInsertHandler
}