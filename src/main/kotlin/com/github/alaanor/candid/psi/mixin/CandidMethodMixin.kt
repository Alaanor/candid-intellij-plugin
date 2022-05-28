package com.github.alaanor.candid.psi.mixin

import com.github.alaanor.candid.icon.CandidIcons
import com.github.alaanor.candid.psi.*
import com.github.alaanor.candid.psi.primitive.CandidStubBasedElementBase
import com.github.alaanor.candid.psi.stub.CandidStubBasedPsiElement
import com.github.alaanor.candid.psi.stub.impl.CandidMethodStub
import com.github.alaanor.candid.reference.CandidMethodReference
import com.intellij.lang.ASTNode
import com.intellij.navigation.ItemPresentation
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiReference
import com.intellij.psi.stubs.IStubElementType
import javax.swing.Icon

abstract class CandidMethodMixin :
    CandidStubBasedElementBase<CandidMethodStub>,
    CandidStubBasedPsiElement<CandidMethodStub, CandidMethodType>,
    CandidMethodType,
    ItemPresentation {

    constructor(node: ASTNode) : super(node)
    constructor(stub: CandidMethodStub, type: IStubElementType<*, *>) : super(stub, type)

    override fun getName(): String? {
        if (methodName.stringLiteral !== null)
            return methodName.stringLiteral?.textWithoutQuote()
        return methodName.text
    }

    override fun getPresentableText(): String = name ?: "Unnamed method"
    override fun getTextOffset(): Int = methodName.textOffset

    override fun getTextRange(): TextRange {
        if (methodName.stringLiteral !== null)
            return methodName.stringLiteral?.getTextRangeWithoutQuote() ?: node.textRange
        return methodName.textRange
    }

    override fun getReference(): PsiReference? {
        return CandidMethodReference(this, TextRange.create(0, methodName.textLength))
    }

    override fun getIcon(unused: Boolean): Icon {
        return when (getMethodType()) {
            Type.Update -> CandidIcons.MethodUpdate
            Type.Query -> CandidIcons.MethodQuery
            Type.OneWay -> CandidIcons.MethodOneway
        }
    }

    enum class Type {
        Update,
        Query,
        OneWay
    }

    private fun getMethodType(): Type {
        val annotation = children.find { it is CandidFuncAnnotation }
            ?: return Type.Update

        return when (annotation.text) {
            "query" -> Type.Query
            "oneway" -> Type.OneWay
            else -> Type.Update
        }
    }
}