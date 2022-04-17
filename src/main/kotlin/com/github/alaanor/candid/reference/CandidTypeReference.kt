package com.github.alaanor.candid.reference

import com.github.alaanor.candid.CandidFileType
import com.github.alaanor.candid.psi.CandidElementFactory
import com.github.alaanor.candid.psi.CandidIdentifierDeclaration
import com.github.alaanor.candid.psi.CandidIdentifierReference
import com.intellij.codeInsight.daemon.EmptyResolveMessageProvider
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.icons.AllIcons
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiManager
import com.intellij.psi.PsiReferenceBase
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.indexing.FileBasedIndex

class CandidTypeReference(identifierReference: CandidIdentifierReference, private var textRange: TextRange) :
    PsiReferenceBase<CandidIdentifierReference>(identifierReference, textRange), EmptyResolveMessageProvider {

    override fun calculateDefaultRangeInElement(): TextRange = element.textRange
    override fun getRangeInElement(): TextRange = textRange
    override fun getUnresolvedMessagePattern(): String = "Unresolved type"

    override fun resolve(): PsiElement? {
        return PsiTreeUtil.findChildrenOfType(element.containingFile, CandidIdentifierDeclaration::class.java)
            .find { element.text == it.text }
    }

    override fun isReferenceTo(element: PsiElement): Boolean {
        when (element) {
            is CandidIdentifierDeclaration -> if (element.nameIdentifier?.text == this.element.text)
                return true
        }
        return false
    }

    override fun handleElementRename(newElementName: String): PsiElement {
        val newElement = CandidElementFactory.createTypeReference(element.project, newElementName)
        return element.replace(newElement)
    }

    override fun getVariants(): Array<Any> {
        val files = FileBasedIndex.getInstance().getContainingFiles(
            FileTypeIndex.NAME,
            CandidFileType.INSTANCE,
            GlobalSearchScope.projectScope(element.project)
        )
        val psiManager = PsiManager.getInstance(element.project)

        return files.mapNotNull { file ->
            val psiFile = psiManager.findFile(file)
            val types = PsiTreeUtil.findChildrenOfType(psiFile, CandidIdentifierDeclaration::class.java)
            types.mapNotNull {
                LookupElementBuilder.create(it)
                    .withTypeText("type")
                    .withIcon(AllIcons.Nodes.Type)
            }
        }.flatten().toTypedArray()
    }

}