package com.github.alaanor.candid.annotator

import com.github.alaanor.candid.psi.CandidImportStatement
import com.github.alaanor.candid.psi.getTextRangeWithoutQuote
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement

class CandidUnresolvedImportAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        val import = element as? CandidImportStatement ?: return
        val reference = element.reference ?: return
        val resolved = reference.resolve() != null

        if (resolved) return
        if (import.stringLiteral?.textLength == 2) {
            // empty string with 2 quote
            val range = import.stringLiteral?.getTextRangeWithoutQuote()?.shiftLeft(1)?.grown(2) ?: import.textRange
            holder.newAnnotation(HighlightSeverity.ERROR, "Empty import")
                .range(range)
                .highlightType(ProblemHighlightType.GENERIC_ERROR)
                .create()
            return
        }

        val range = import.stringLiteral?.getTextRangeWithoutQuote()?: import.textRange

        holder.newAnnotation(HighlightSeverity.ERROR, "Unresolved import")
            .range(range)
            .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
            .create()
    }
}