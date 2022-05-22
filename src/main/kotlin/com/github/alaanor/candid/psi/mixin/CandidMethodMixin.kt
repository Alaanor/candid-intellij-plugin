package com.github.alaanor.candid.psi.mixin

import com.github.alaanor.candid.icon.CandidIcons
import com.github.alaanor.candid.psi.CandidFuncAnnotation
import com.github.alaanor.candid.psi.CandidMethType
import com.github.alaanor.candid.psi.CandidMethodName
import com.github.alaanor.candid.psi.primitive.CandidElementBase
import com.intellij.lang.ASTNode
import com.intellij.navigation.ItemPresentation
import javax.swing.Icon

abstract class CandidMethodMixin(node: ASTNode) : CandidElementBase(node), CandidMethType, ItemPresentation {
    override fun getName(): String? = children.find { it is CandidMethodName }?.text
    override fun getPresentableText(): String = name ?: "Unnamed method"

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