package com.github.alaanor.candid

import com.github.alaanor.candid.psi.CandidFile
import com.github.alaanor.candid.psi.impl.CandidDataTypeImpl
import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PatternCondition
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiErrorElement
import com.intellij.util.ProcessingContext

class CandidCompletionContributor : CompletionContributor() {
    init {
        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement()
                .afterLeaf("=")
                .withSuperParent(2, CandidDataTypeImpl::class.java),
            KeywordsProvider.primaryType
        )

        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement()
                .andNot(PlatformPatterns.psiElement().beforeLeaf(";"))
                .withParent(PsiErrorElement::class.java)
                .withSuperParent(2, CandidFile::class.java),
            KeywordsProvider.topLevelKeyword
        )
    }
}

class KeywordsProvider(keywords: List<String>) : CompletionProvider<CompletionParameters>() {

    companion object {
        val primaryType
            get() = KeywordsProvider(
                listOf(
                    "nat", "nat8", "nat16", "nat32", "nat64",
                    "int", "int8", "int16", "int32", "int64",
                    "float32", "float64", "bool", "text",
                    "null", "reserved", "empty", "principal"
                )
            )

        val topLevelKeyword
            get() = KeywordsProvider(
                listOf(
                    "type", "service"
                )
            )
    }

    private val keywordElements = keywords.map { LookupElementBuilder.create(it) }

    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet
    ) {
        result.addAllElements(keywordElements)
    }
}