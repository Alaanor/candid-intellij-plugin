package com.github.alaanor.candid.psi.stub.impl

import com.github.alaanor.candid.psi.CandidIdentifierDeclaration
import com.github.alaanor.candid.psi.stub.type.CandidIdentifierDeclarationStubType
import com.intellij.psi.stubs.StubElement

class CandidIdentifierDeclarationStub(override val name: String, parent: StubElement<*>?) :
    CandidStubBase<CandidIdentifierDeclaration>(parent, CandidIdentifierDeclarationStubType)