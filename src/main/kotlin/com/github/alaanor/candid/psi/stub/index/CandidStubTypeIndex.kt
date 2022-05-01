package com.github.alaanor.candid.psi.stub.index

import com.github.alaanor.candid.psi.CandidIdentifierDeclaration
import com.intellij.psi.stubs.StringStubIndexExtension
import com.intellij.psi.stubs.StubIndexKey

class CandidStubTypeIndex : StringStubIndexExtension<CandidIdentifierDeclaration>() {
    companion object {
        val Key = StubIndexKey.createIndexKey<String, CandidIdentifierDeclaration>("candid.type.name")
    }

    override fun getKey(): StubIndexKey<String, CandidIdentifierDeclaration> {
        return Key
    }
}