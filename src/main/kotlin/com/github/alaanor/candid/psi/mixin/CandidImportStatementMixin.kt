package com.github.alaanor.candid.psi.mixin

import com.github.alaanor.candid.icon.CandidIcons
import com.github.alaanor.candid.psi.CandidImportStatement
import com.github.alaanor.candid.psi.getTextRangeWithoutQuote
import com.github.alaanor.candid.psi.importedPsiFile
import com.github.alaanor.candid.psi.primitive.CandidElementBase
import com.github.alaanor.candid.reference.CandidImportReference
import com.github.alaanor.candid.util.projectFilePath
import com.intellij.lang.ASTNode
import com.intellij.navigation.ItemPresentation
import com.intellij.psi.PsiReference
import javax.swing.Icon

abstract class CandidImportStatementMixin(node: ASTNode) : CandidElementBase(node), CandidImportStatement, ItemPresentation {

    override fun getReference(): PsiReference? {
        return stringLiteral?.getTextRangeWithoutQuote()?.let {
            CandidImportReference(this, it)
        }
    }

    override fun getIcon(unused: Boolean): Icon = CandidIcons.Import
    override fun getPresentableText(): String = importedPsiFile()?.projectFilePath() ?: "Invalid import"
}