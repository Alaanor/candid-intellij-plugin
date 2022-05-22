package com.github.alaanor.candid.reference

import com.github.alaanor.candid.psi.CandidIdentifierDeclaration
import com.github.alaanor.candid.psi.stub.index.CandidStubTypeIndex
import com.intellij.navigation.ChooseByNameContributor
import com.intellij.navigation.NavigationItem
import com.intellij.openapi.project.Project
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.StubIndex

class CandidGoToSymbolContributor : ChooseByNameContributor {
    override fun getNames(project: Project, includeNonProjectItems: Boolean): Array<String> {
        return StubIndex.getInstance()
            .getAllKeys(CandidStubTypeIndex.Key, project)
            .toTypedArray()
    }

    override fun getItemsByName(
        name: String,
        pattern: String?,
        project: Project,
        includeNonProjectItems: Boolean
    ): Array<NavigationItem> {
        return StubIndex.getElements(
            CandidStubTypeIndex.Key,
            name,
            project,
            GlobalSearchScope.projectScope(project),
            CandidIdentifierDeclaration::class.java
        ).filterIsInstance<NavigationItem>().toTypedArray()
    }
}