package com.github.alaanor.candid.psi.mixin

import com.github.alaanor.candid.psi.CandidStringLiteral
import com.github.alaanor.candid.psi.primitive.CandidElementBase
import com.intellij.lang.ASTNode

abstract class CandidStringLiteralMixin(node: ASTNode) : CandidElementBase(node), CandidStringLiteral