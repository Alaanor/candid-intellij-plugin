package com.github.alaanor.candid.reference

import com.github.alaanor.candid.psi.CandidElementFactory
import com.github.alaanor.candid.psi.CandidIdentifierDeclaration
import com.github.alaanor.candid.psi.CandidIdentifierReference
import com.github.alaanor.candid.psi.stub.index.CandidStubTypeIndex
import com.github.alaanor.candid.util.CandidImportUtil
import com.intellij.codeInsight.daemon.EmptyResolveMessageProvider
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.icons.AllIcons
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReferenceBase
import com.intellij.psi.stubs.StubIndex
import com.intellij.psi.util.PsiTreeUtil

class CandidTypeReference(identifierReference: CandidIdentifierReference, private var textRange: TextRange) :
    PsiReferenceBase<CandidIdentifierReference>(identifierReference, textRange), EmptyResolveMessageProvider {

    override fun calculateDefaultRangeInElement(): TextRange = element.textRange
    override fun getRangeInElement(): TextRange = textRange
    override fun getUnresolvedMessagePattern(): String = "Unresolved type"

    override fun resolve(): PsiElement? {
        return resolve(this.element)
    }

    override fun isReferenceTo(element: PsiElement): Boolean {
        when (element) {
            is CandidIdentifierDeclaration -> {
                return element.nameIdentifier?.text == this.element.text
                        && resolve(this.element) == element
            }
        }
        return false
    }

    override fun handleElementRename(newElementName: String): PsiElement {
        val newElement = CandidElementFactory.createTypeReference(element.project, newElementName)
        return element.replace(newElement)
    }

    override fun getVariants(): Array<Any> {
        return StubIndex.getInstance().getAllKeys(CandidStubTypeIndex.Key, element.project).mapNotNull {
            LookupElementBuilder.create(it)
                .withTypeText("type")
                .withIcon(AllIcons.Nodes.Type)
        }.toTypedArray()
    }

    private fun resolve(element: PsiElement): PsiElement? {
        // local first
        PsiTreeUtil
            .findChildrenOfType(element.containingFile, CandidIdentifierDeclaration::class.java)
            .find { element.text == it.text }
            ?.let { return it }

        // search further
        val files = CandidImportUtil.getAllImportedFileFor(element.containingFile, true)
        files.forEach { file ->
            PsiTreeUtil
                .findChildrenOfType(file, CandidIdentifierDeclaration::class.java)
                .find { element.text == it.text }
                ?.let { return it }
        }

        return null
    }
}