package com.github.alaanor.candid.psi.stub.type

import com.github.alaanor.candid.psi.CandidImportStatement
import com.github.alaanor.candid.psi.impl.CandidImportStatementImpl
import com.github.alaanor.candid.psi.importPathString
import com.github.alaanor.candid.psi.stub.impl.CandidImportStatementStub
import com.intellij.psi.PsiElement
import com.intellij.psi.stubs.StubElement

object CandidImportStatementStubType :
    CandidStubTypeBase<CandidImportStatementStub, CandidImportStatement>("CANDID_IMPORT") {
    override fun getExternalId(): String {
        return "candid.import.stub"
    }

    override fun createStub(name: String, parentStub: StubElement<*>?): CandidImportStatementStub {
        return CandidImportStatementStub(name, parentStub)
    }

    override fun createStub(
        psi: CandidImportStatement,
        parentStub: StubElement<out PsiElement>?
    ): CandidImportStatementStub {
        return createStub(psi.importPathString() ?: "", parentStub)
    }

    override fun createPsi(stub: CandidImportStatementStub): CandidImportStatement {
        return CandidImportStatementImpl(stub, stub.stubType)
    }
}