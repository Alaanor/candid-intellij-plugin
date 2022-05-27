package com.github.alaanor.candid.completion

import com.github.alaanor.candid.psi.CandidActor
import com.github.alaanor.candid.psi.CandidMethodType
import com.intellij.codeInsight.completion.InsertHandler
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.patterns.ElementPattern
import com.intellij.patterns.PatternCondition
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.psi.PsiElement
import com.intellij.psi.TokenType
import com.intellij.util.ProcessingContext

class MethodAnnotationCompletion : CandidBasicCompletion() {
    override val elementPattern: ElementPattern<out PsiElement> = psiElement()
        .afterSiblingSkipping(
            psiElement(TokenType.WHITE_SPACE),
            psiElement(CandidActor::class.java)
                .with(object : PatternCondition<CandidActor>("bruh") {
                    override fun accepts(t: CandidActor, context: ProcessingContext?): Boolean {
                        return t.lastChild is CandidMethodType
                    }
                })
        )

    override val tokenList: List<String> = listOf("query", "oneway")
    override fun keywordInsertHandle(keyword: String): InsertHandler<LookupElement> = EmptyInsertHandler
}