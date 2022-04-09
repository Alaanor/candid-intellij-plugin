package com.github.alaanor.candid.completion

import com.github.alaanor.candid.psi.impl.CandidDataTypeImpl
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.patterns.ElementPattern
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiElement

class DataTypeCompletion : CandidBasicCompletion() {
    override val elementPattern: ElementPattern<out PsiElement> = PlatformPatterns.psiElement()
        .afterLeaf("=")
        .withSuperParent(2, CandidDataTypeImpl::class.java)

    override val completionProvider: CompletionProvider<CompletionParameters> = KeywordsProvider(
        listOf(
            "nat", "nat8", "nat16", "nat32", "nat64",
            "int", "int8", "int16", "int32", "int64",
            "float32", "float64", "bool", "text",
            "null", "reserved", "empty", "principal",
            "opt", "vec", "record", "variant",
            "func", "service"
        ),
        insertHandler = SpaceAfterInsertHandler
    )
}