package com.github.alaanor.candid.psi.mixin

import com.github.alaanor.candid.psi.CandidDefinition
import com.github.alaanor.candid.psi.primitive.CandidNameIdentifierOwnerImpl
import com.intellij.lang.ASTNode

abstract class CandidDefinitionMixin(node: ASTNode) : CandidNameIdentifierOwnerImpl(node), CandidDefinition