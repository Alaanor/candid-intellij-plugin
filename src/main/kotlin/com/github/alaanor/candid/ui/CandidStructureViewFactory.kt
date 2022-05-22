package com.github.alaanor.candid.ui

import com.github.alaanor.candid.psi.CandidDefinition
import com.github.alaanor.candid.psi.CandidFile
import com.github.alaanor.candid.psi.CandidIdentifierDeclaration
import com.github.alaanor.candid.psi.CandidImportStatement
import com.intellij.ide.structureView.*
import com.intellij.ide.util.treeView.smartTree.SortableTreeElement
import com.intellij.ide.util.treeView.smartTree.TreeElement
import com.intellij.lang.PsiStructureViewFactory
import com.intellij.navigation.ItemPresentation
import com.intellij.openapi.editor.Editor
import com.intellij.psi.NavigatablePsiElement
import com.intellij.psi.PsiFile

class CandidStructureViewFactory : PsiStructureViewFactory {
    override fun getStructureViewBuilder(psiFile: PsiFile): StructureViewBuilder? {
        val candidFile = psiFile as? CandidFile ?: return null

        return object : TreeBasedStructureViewBuilder() {
            override fun createStructureViewModel(editor: Editor?): StructureViewModel {
                return CandidStructureViewModel(candidFile, editor)
            }
        }
    }
}

class CandidStructureViewModel(psiFile: PsiFile, editor: Editor?) :
    StructureViewModelBase(psiFile, editor, CandidStructureViewElement(psiFile)),
    StructureViewModel.ElementInfoProvider {
    override fun isAlwaysShowsPlus(element: StructureViewTreeElement?): Boolean {
        return element?.value is CandidFile
    }

    override fun isAlwaysLeaf(element: StructureViewTreeElement?): Boolean {
        return when (element?.value) {
            is CandidImportStatement -> true
            is CandidIdentifierDeclaration -> true
            else -> false
        }
    }
}

class CandidStructureViewElement(private val element: NavigatablePsiElement) : StructureViewTreeElement,
    SortableTreeElement {
    override fun getPresentation(): ItemPresentation = element.presentation ?: TODO()
    override fun navigate(requestFocus: Boolean) = element.navigate(requestFocus)
    override fun canNavigate(): Boolean = element.canNavigate()
    override fun canNavigateToSource(): Boolean = element.canNavigateToSource()
    override fun getValue(): Any = element
    override fun getAlphaSortKey(): String = element.name ?: ""

    override fun getChildren(): Array<TreeElement> {
        return element.children
            .mapNotNull {
                val element = when (it) {
                    is CandidDefinition -> {
                        it.children.find { child -> child is CandidIdentifierDeclaration }
                    }
                    else -> it
                }

                if (element !is ItemPresentation) return@mapNotNull null
                val navigablePsiElement = element as? NavigatablePsiElement ?: return@mapNotNull null

                CandidStructureViewElement(navigablePsiElement)
            }
            .toTypedArray()
    }

}