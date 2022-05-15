package com.github.alaanor.candid.psi.stub.index

import com.github.alaanor.candid.psi.CandidImportStatement
import com.intellij.psi.stubs.StringStubIndexExtension
import com.intellij.psi.stubs.StubIndexKey

class CandidStubImportIndex: StringStubIndexExtension<CandidImportStatement>() {
    companion object {
        val Key = StubIndexKey.createIndexKey<String,CandidImportStatement>("candid.import.path")
    }

    override fun getKey(): StubIndexKey<String, CandidImportStatement> = Key
}