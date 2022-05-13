package com.github.alaanor.candid.completion

import com.github.alaanor.candid.psi.impl.CandidDataTypeImpl
import com.github.alaanor.candid.psi.impl.CandidFieldTypeRecordImpl
import com.github.alaanor.candid.psi.impl.CandidFieldTypeVariantImpl
import com.github.alaanor.candid.psi.impl.CandidRecordStatementImpl
import com.github.alaanor.candid.psi.impl.CandidTupTypeImpl
import com.github.alaanor.candid.psi.impl.CandidVariantStatementImpl
import com.intellij.codeInsight.completion.InsertHandler
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.patterns.ElementPattern
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.psi.PsiElement

val sharedDataTypeElementPattern = psiElement().andOr(
    psiElement()
        .afterLeaf("=")
        .withSuperParent(2, CandidDataTypeImpl::class.java),
    psiElement()
        .afterLeaf(":")
        .withSuperParent(2, CandidDataTypeImpl::class.java)
        .andOr(
            psiElement()
                .withSuperParent(3, CandidFieldTypeRecordImpl::class.java)
                .withSuperParent(4, CandidRecordStatementImpl::class.java),
            psiElement()
                .withSuperParent(3, CandidFieldTypeVariantImpl::class.java)
                .withSuperParent(4, CandidVariantStatementImpl::class.java),
        ),
    psiElement()
        .withSuperParent(2, CandidDataTypeImpl::class.java)
        .withSuperParent(3, CandidTupTypeImpl::class.java)
)

class DataTypeCompletion : CandidBasicCompletion() {
    override val elementPattern: ElementPattern<out PsiElement> = psiElement().andOr(
        sharedDataTypeElementPattern,
        psiElement()
            .andOr(
                psiElement().afterLeaf("opt"),
                psiElement().afterLeaf("vec")
            )
            .withSuperParent(2, CandidDataTypeImpl::class.java)
    )

    override val tokenList: List<String> = listOf(
        "nat", "nat8", "nat16", "nat32", "nat64",
        "int", "int8", "int16", "int32", "int64",
        "float32", "float64", "bool", "text",
        "null", "reserved", "empty", "principal",
        "record", "variant", "func", "service",
        "blob"
    )

    override fun keywordInsertHandle(keyword: String): InsertHandler<LookupElement> {
        return when (keyword) {
            "record", "variant" -> BraceAfterInsertHandler
            "vec", "opt", "service" -> SpaceAfterInsertHandler
            "func" -> FuncParenAfterInsertHandler
            else -> EmptyInsertHandler
        }
    }
}

class VecOptTypeCompletion : CandidBasicCompletion() {
    override val elementPattern: ElementPattern<out PsiElement> = sharedDataTypeElementPattern
    override val tokenList: List<String> = listOf("opt", "vec")
    override fun keywordInsertHandle(keyword: String): InsertHandler<LookupElement> = SpaceAfterInsertHandler
}