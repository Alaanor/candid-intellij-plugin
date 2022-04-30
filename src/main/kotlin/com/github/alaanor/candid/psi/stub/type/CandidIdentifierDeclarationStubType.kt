package com.github.alaanor.candid.psi.stub.type

import com.github.alaanor.candid.psi.CandidIdentifierDeclaration
import com.github.alaanor.candid.psi.impl.CandidIdentifierDeclarationImpl
import com.github.alaanor.candid.psi.stub.impl.CandidIdentifierDeclarationStub
import com.intellij.psi.PsiElement
import com.intellij.psi.stubs.StubElement

object CandidIdentifierDeclarationStubType :
    CandidStubTypeBase<CandidIdentifierDeclarationStub, CandidIdentifierDeclaration>("CANDID_TYPE_DEFINITION") {
    override fun getExternalId(): String {
        return "candid.type.stub"
    }

    override fun createPsi(stub: CandidIdentifierDeclarationStub): CandidIdentifierDeclaration {
        return CandidIdentifierDeclarationImpl(stub, stub.stubType)
    }

    override fun createStub(
        psi: CandidIdentifierDeclaration,
        parentStub: StubElement<out PsiElement>
    ): CandidIdentifierDeclarationStub {
        return createStub(psi.name ?: "", parentStub)
    }

    override fun createStub(name: String, parentStub: StubElement<*>?): CandidIdentifierDeclarationStub {
        return CandidIdentifierDeclarationStub(name, parentStub)
    }
}