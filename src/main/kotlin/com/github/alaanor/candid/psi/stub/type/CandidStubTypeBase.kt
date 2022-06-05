package com.github.alaanor.candid.psi.stub.type

import com.github.alaanor.candid.CandidLanguage
import com.github.alaanor.candid.psi.stub.impl.CandidIdentifierDeclarationStub
import com.github.alaanor.candid.psi.stub.impl.CandidMethodStub
import com.github.alaanor.candid.psi.stub.impl.CandidStubBase
import com.github.alaanor.candid.psi.stub.index.CandidStubMethodIndex
import com.github.alaanor.candid.psi.stub.index.CandidStubTypeIndex
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
        when (stub) {
            is CandidIdentifierDeclarationStub -> sink.occurrence(CandidStubTypeIndex.Key, stub.name)
            is CandidMethodStub -> sink.occurrence(CandidStubMethodIndex.Key, stub.name)
        }
    }

    abstract fun createStub(name: String, parentStub: StubElement<*>?) : TStub
}