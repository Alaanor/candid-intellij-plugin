package com.github.alaanor.candid.psi.stub.index

import com.github.alaanor.candid.psi.CandidMethodType
import com.intellij.psi.stubs.StringStubIndexExtension
import com.intellij.psi.stubs.StubIndexKey

class CandidStubMethodIndex: StringStubIndexExtension<CandidMethodType>() {
    companion object {
        val Key = StubIndexKey.createIndexKey<String, CandidMethodType>("candid.method.name")
    }

    override fun getKey(): StubIndexKey<String, CandidMethodType> {
        return Key
    }
}