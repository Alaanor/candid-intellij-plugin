package com.github.alaanor.candid.psi.stub

import com.intellij.psi.PsiElement
import com.intellij.psi.StubBasedPsiElement
import com.intellij.psi.stubs.StubElement

interface CandidStubBasedPsiElement<TStub: StubElement<TPsi>, TPsi: PsiElement> : StubBasedPsiElement<TStub> {

}