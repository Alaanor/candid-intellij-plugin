package com.github.alaanor.candid.psi.stub.impl

import com.github.alaanor.candid.psi.stub.CandidStub
import com.intellij.psi.PsiElement
import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.stubs.StubBase
import com.intellij.psi.stubs.StubElement

abstract class CandidStubBase<T: PsiElement>(
    parent: StubElement<*>?,
    type: IStubElementType<*, *>
) : StubBase<T>(parent, type), CandidStub<T>