package com.github.alaanor.candid.psi.mixin

import com.github.alaanor.candid.psi.CandidImportStatement
import com.github.alaanor.candid.psi.primitive.CandidStubBasedElementBase
import com.github.alaanor.candid.psi.stub.impl.CandidImportStatementStub
import com.github.alaanor.candid.reference.CandidImportReference
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiReference
import com.intellij.psi.stubs.IStubElementType

abstract class CandidImportStatementMixin :
    CandidStubBasedElementBase<CandidImportStatementStub>,
    CandidImportStatement {

    constructor(node: ASTNode) : super(node)
    constructor(stub: CandidImportStatementStub, type: IStubElementType<*, *>) : super(stub, type)

    override fun getReference(): PsiReference? {
        return stringLiteral?.textRange?.let {
            CandidImportReference(this, it)
        }
    }
}