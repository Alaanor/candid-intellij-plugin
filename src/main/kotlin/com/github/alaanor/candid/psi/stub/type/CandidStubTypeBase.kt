package com.github.alaanor.candid.psi.stub.type

import com.github.alaanor.candid.CandidLanguage
import com.github.alaanor.candid.psi.stub.CandidStubBasedPsiElement
import com.github.alaanor.candid.psi.stub.impl.CandidStubBase
import com.intellij.psi.PsiElement
import com.intellij.psi.stubs.*

abstract class CandidStubTypeBase<TStub: CandidStubBase<TPsi>, TPsi : PsiElement> (name: String)
    : IStubElementType<TStub, TPsi> (name, CandidLanguage.INSTANCE) {

    override fun serialize(stub: TStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
    }

    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): TStub {
        return createStub(dataStream.readNameString() ?: "", parentStub)
    }

    override fun indexStub(stub: TStub, sink: IndexSink) {
        TODO("Not yet implemented")
    }

    abstract fun createStub(name: String, parentStub: StubElement<*>?) : TStub
}