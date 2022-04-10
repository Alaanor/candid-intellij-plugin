package com.github.alaanor.candid.completion

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.ElementPattern
import com.intellij.psi.PsiElement
import com.intellij.util.ProcessingContext

class CandidCompletionContributor : CompletionContributor() {
    init {
        extendWithBasic(TopLevelKeywordCompletion())
        extendWithBasic(DataTypeCompletion())
        extendWithBasic(VecOptTypeCompletion())
    }

    private fun extendWithBasic(candidBasicCompletion: CandidBasicCompletion) {
        extend(
            CompletionType.BASIC,
            candidBasicCompletion.elementPattern,
            KeywordsProvider(
                candidBasicCompletion.tokenList,
                candidBasicCompletion::keywordInsertHandle
            )
        )
    }
}

abstract class CandidBasicCompletion {
    abstract val elementPattern: ElementPattern<out PsiElement>
    abstract val tokenList: List<String>
    abstract fun keywordInsertHandle(keyword: String): InsertHandler<LookupElement>
}

class KeywordsProvider(
    keywords: List<String>,
    insertHandler: (keyword: String) -> InsertHandler<LookupElement>,
) : CompletionProvider<CompletionParameters>() {

    private val keywordElements = keywords.map {
        LookupElementBuilder.create(it)
            .withTypeText("keyword")
            .withInsertHandler(insertHandler(it))
    }

    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet
    ) {
        result.addAllElements(keywordElements)
    }


}