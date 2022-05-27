package com.github.alaanor.candid.psi.stub.impl

import com.github.alaanor.candid.psi.CandidMethodType
import com.github.alaanor.candid.psi.stub.type.CandidMethodStubType
import com.intellij.psi.stubs.StubElement

class CandidMethodStub(override val name: String, parent: StubElement<*>?) :
    CandidStubBase<CandidMethodType>(parent, CandidMethodStubType)