package com.github.alaanor.candid.psi.mixin

import com.github.alaanor.candid.icon.CandidIcons
import com.github.alaanor.candid.psi.CandidActor
import com.github.alaanor.candid.psi.CandidActorName
import com.github.alaanor.candid.psi.primitive.CandidElementBase
import com.intellij.lang.ASTNode
import com.intellij.navigation.ItemPresentation
import javax.swing.Icon

abstract class CandidServiceMixin(node: ASTNode) : CandidElementBase(node), CandidActor, ItemPresentation {
    override fun getName(): String? = children.find { it is CandidActorName }?.text
    override fun getIcon(unused: Boolean): Icon = CandidIcons.Service
    override fun getPresentableText(): String = name ?: "Service"
}