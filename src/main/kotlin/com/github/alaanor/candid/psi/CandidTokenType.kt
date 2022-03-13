package com.github.alaanor.candid.psi

import com.github.alaanor.candid.CandidLanguage
import com.intellij.psi.tree.IElementType

class CandidTokenType(debugName: String) : IElementType(debugName, CandidLanguage.INSTANCE) {
    override fun toString(): String {
        return "CandidTokenType.${super.toString()}"
    }
}