package com.github.alaanor.candid.psi.mixin

import com.github.alaanor.candid.psi.CandidElementFactory
import com.github.alaanor.candid.psi.CandidIdentifierDeclaration
import com.github.alaanor.candid.psi.primitive.CandidNameIdentifierOwner
import com.github.alaanor.candid.psi.primitive.CandidStubBasedElementBase
import com.github.alaanor.candid.psi.stub.CandidStubBasedPsiElement
import com.github.alaanor.candid.psi.stub.impl.CandidIdentifierDeclarationStub
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.stubs.IStubElementType

abstract class CandidIdentifierDeclarationMixin :
    CandidStubBasedElementBase<CandidIdentifierDeclarationStub>,
    CandidNameIdentifierOwner,
    CandidStubBasedPsiElement<CandidIdentifierDeclarationStub, CandidIdentifierDeclaration>,
    CandidIdentifierDeclaration {

    constructor(node: ASTNode) : super(node)

    constructor(stub: CandidIdentifierDeclarationStub, type: IStubElementType<*, *>): super(stub, type)

    override fun getNameIdentifier(): PsiElement = node.psi
    override fun getTextOffset(): Int = node.startOffset
    override fun getName(): String? = node.text

    override fun setName(name: String): PsiElement {
        nameIdentifier.replace(CandidElementFactory.createTypeDeclaration(project, name))
        return this
    }
}