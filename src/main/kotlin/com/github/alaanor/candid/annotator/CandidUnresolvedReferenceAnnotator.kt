package com.github.alaanor.candid.annotator

import com.github.alaanor.candid.psi.CandidIdentifierReference
import com.github.alaanor.candid.quickfix.CandidAddImportQuickFix
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement

class CandidUnresolvedReferenceAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        if (element !is CandidIdentifierReference)
            return

        val reference = element.reference ?: return
        val resolved = reference.resolve() != null
        if (resolved) return

        holder.newAnnotation(HighlightSeverity.ERROR, "Unresolved reference")
            .range(element)
            .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
            .withFix(CandidAddImportQuickFix(element))
            .create()
    }
}