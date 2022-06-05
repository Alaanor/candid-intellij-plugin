package com.github.alaanor.candid.psi.stub.type

import com.intellij.psi.tree.IElementType

object CandidStubTypes {
    @JvmStatic
    fun get(name: String): IElementType {
        return when (name) {
            "IDENTIFIER_DECLARATION" -> CandidIdentifierDeclarationStubType
            "METHOD_TYPE" -> CandidMethodStubType
            else -> throw UnsupportedOperationException("Unsupported stub type")
        }
    }
}