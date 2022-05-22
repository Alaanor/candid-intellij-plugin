package com.github.alaanor.candid.psi.primitive

import com.github.alaanor.candid.psi.stub.impl.CandidStubBase
import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.extapi.psi.StubBasedPsiElementBase
import com.intellij.lang.ASTNode
import com.intellij.navigation.ItemPresentation
import com.intellij.psi.stubs.IStubElementType

abstract class CandidStubBasedElementBase<T : CandidStubBase<*>> : StubBasedPsiElementBase<T> {
    constructor(node: ASTNode) : super(node)
    constructor(stub: T, type: IStubElementType<*, *>) : super(stub, type)

    override fun getPresentation(): ItemPresentation? {
        return if (this is ItemPresentation) this else null
    }
}