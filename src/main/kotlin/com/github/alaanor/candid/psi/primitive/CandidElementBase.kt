package com.github.alaanor.candid.psi.primitive

import com.github.alaanor.candid.psi.CandidElement
import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode

abstract class CandidElementBase(node: ASTNode) : ASTWrapperPsiElement(node), CandidElement