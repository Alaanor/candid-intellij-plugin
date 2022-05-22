package com.github.alaanor.candid.psi.primitive

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.navigation.ItemPresentation

abstract class CandidElementBase(node: ASTNode) : ASTWrapperPsiElement(node) {
    override fun getPresentation(): ItemPresentation? {
        return if (this is ItemPresentation) this else null
    }
}