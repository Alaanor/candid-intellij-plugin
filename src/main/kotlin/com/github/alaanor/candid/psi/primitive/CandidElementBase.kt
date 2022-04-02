package com.github.alaanor.candid.psi.primitive

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode

abstract class CandidElementBase(node: ASTNode) : ASTWrapperPsiElement(node)