package com.github.alaanor.candid.psi.stub

import com.intellij.psi.PsiElement
import com.intellij.psi.stubs.StubElement

interface CandidStub<T : PsiElement> : StubElement<T> {
    val name: String
}