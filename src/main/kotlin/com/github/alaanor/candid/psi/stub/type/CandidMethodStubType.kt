package com.github.alaanor.candid.psi.stub.type

import com.github.alaanor.candid.psi.CandidMethodType
import com.github.alaanor.candid.psi.impl.CandidMethodTypeImpl
import com.github.alaanor.candid.psi.stub.impl.CandidMethodStub
import com.intellij.psi.PsiElement
import com.intellij.psi.stubs.StubElement

object CandidMethodStubType : CandidStubTypeBase<CandidMethodStub, CandidMethodType>("CANDID_METHOD") {
    override fun getExternalId(): String = "candid.method.stub"

    override fun createStub(name: String, parentStub: StubElement<*>?): CandidMethodStub {
        return CandidMethodStub(name, parentStub)
    }

    override fun createPsi(stub: CandidMethodStub): CandidMethodType {
        return CandidMethodTypeImpl(stub, stub.stubType)
    }

    override fun createStub(psi: CandidMethodType, parentStub: StubElement<out PsiElement>?): CandidMethodStub {
        return CandidMethodStub(psi.methodName.text, parentStub)
    }
}