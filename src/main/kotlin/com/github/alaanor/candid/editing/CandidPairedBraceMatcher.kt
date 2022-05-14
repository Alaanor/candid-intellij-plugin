package com.github.alaanor.candid.editing

import com.github.alaanor.candid.CandidTypes
import com.intellij.lang.BracePair
import com.intellij.lang.PairedBraceMatcher
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType

class CandidPairedBraceMatcher : PairedBraceMatcher {
    companion object {
        private val Pairs = arrayOf(
            BracePair(CandidTypes.LBRACE, CandidTypes.RBRACE, true),
            BracePair(CandidTypes.LPAREN, CandidTypes.RPAREN, false)
        )
    }
    override fun getPairs(): Array<BracePair> = Pairs
    override fun isPairedBracesAllowedBeforeType(lbraceType: IElementType, contextType: IElementType?): Boolean = true
    override fun getCodeConstructStart(file: PsiFile?, openingBraceOffset: Int): Int = 0
}