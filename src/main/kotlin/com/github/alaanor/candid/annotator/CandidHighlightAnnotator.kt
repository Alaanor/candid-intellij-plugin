package com.github.alaanor.candid.annotator

import com.github.alaanor.candid.highlight.CandidColor
import com.github.alaanor.candid.psi.CandidIdentifierDeclaration
import com.github.alaanor.candid.psi.CandidIdentifierReference
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement

class CandidHighlightAnnotator: Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        if (element is CandidIdentifierDeclaration || element is CandidIdentifierReference) {
            holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                .textAttributes(CandidColor.IDENTIFIER.textAttributesKey)
                .create()
        }
    }
}