package com.github.alaanor.candid.psi.stub.impl

import com.github.alaanor.candid.psi.CandidImportStatement
import com.github.alaanor.candid.psi.stub.type.CandidImportStatementStubType
import com.intellij.psi.stubs.StubElement

class CandidImportStatementStub(override val name: String, parent: StubElement<*>?) :
    CandidStubBase<CandidImportStatement>(parent, CandidImportStatementStubType)