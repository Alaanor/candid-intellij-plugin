package com.github.alaanor.candid.psi

import com.github.alaanor.candid.CandidTypes
import com.github.alaanor.candid.util.CandidImportUtil
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.util.elementType

fun CandidImportStatement.importPathString(): String? {
    return this.stringLiteral?.text?.trim('"')
}

fun CandidImportStatement.importedPsiFile(): PsiFile? {
    return this.importPathString()?.let {
        CandidImportUtil.resolveRelatively(this.containingFile, it)
    }
}

val PsiElement.rightSiblings: Sequence<PsiElement>
    get() = generateSequence(this.nextSibling) { it.nextSibling }

val PsiElement.leftSiblings: Sequence<PsiElement>
    get() = generateSequence(this.prevSibling) { it.prevSibling }

fun CandidElement.deleteWithSurroundSemicolon() {
    val toDelete = rightSiblings.takeWhile { it is PsiWhiteSpace } + leftSiblings.takeWhile { it is PsiWhiteSpace }
    toDelete.forEach { it.delete() }

    if (nextSibling.elementType == CandidTypes.SEMICOLON) {
        nextSibling.delete()
    }

    delete()
}

fun CandidStringLiteral.getTextRangeWithoutQuote(): TextRange {
    return TextRange.create(node.textRange.startOffset + 1, node.textRange.endOffset - 1)
}