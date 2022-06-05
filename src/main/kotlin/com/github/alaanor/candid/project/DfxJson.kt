package com.github.alaanor.candid.project

import com.github.alaanor.candid.util.projectFilePath
import com.intellij.json.psi.JsonFile
import com.intellij.json.psi.JsonObject
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiFile
import com.intellij.psi.search.*
import org.rust.cargo.project.model.CargoProjectsService
import org.rust.cargo.project.workspace.CargoWorkspace
import org.rust.cargo.project.workspace.PackageOrigin
import org.rust.lang.core.psi.ext.findCargoPackage
import org.rust.openapiext.document
import java.nio.file.Paths

object DfxJson {

    private val cachedRustCanister: MutableMap<Project, List<RustCanister>?> = mutableMapOf()

    fun isRustCanister(project: Project, candidFile: VirtualFile): Boolean {
        return getCached(project)?.find { it.candidFile.path == candidFile.path } != null
    }

    fun getRustCanisterScopeFromCandidFile(project: Project, candidFile: VirtualFile): GlobalSearchScope? {
        return getCached(project)
            ?.find { it.candidFile.path == candidFile.path }
            ?.getScope()
    }

    fun getCandidFileFromRustFile(psiFile: PsiFile): VirtualFile? {
        return getCached(psiFile.project)
            ?.find { it.cargoPackage == psiFile.findCargoPackage() }
            ?.candidFile
    }

    private fun getCached(project: Project): List<RustCanister>? {
        if (!cachedRustCanister.containsKey(project)) {
            cachedRustCanister[project] = getRustCanisters(project)
        }

        return cachedRustCanister[project]
    }

    private fun getRustCanisters(project: Project): List<RustCanister>? {
        // find dfx json
        val document = FilenameIndex
            .getFilesByName(project, "dfx.json", GlobalSearchScope.projectScope(project), false)
            .find { it.projectFilePath() == "dfx.json" }
            ?.run { virtualFile.document } ?: return null

        // retrieving the list of canisters declared from the dfx.json
        val json = PsiDocumentManager.getInstance(project).getPsiFile(document) as? JsonFile ?: return null
        val topLevel = json.topLevelValue as? JsonObject ?: return null
        val canistersProperty = topLevel.findProperty("canisters") ?: return null
        val canisters = (canistersProperty.value as? JsonObject)?.propertyList ?: return null

        // retrieving the list of all existing cargo package in this project
        val cargoProjectsService = project.getServiceIfCreated(CargoProjectsService::class.java) ?: return null
        val allCargoWorkspaces = cargoProjectsService.allProjects.mapNotNull { cargoProject -> cargoProject.workspace }
        val allPackages = allCargoWorkspaces.flatMap { workspace ->
            workspace.packages.filter { item -> item.origin == PackageOrigin.WORKSPACE }
        }

        // finding a matching pair of rust type canister + cargo package
        return canisters.mapNotNull { jsonProperty ->
            // filter by canister type of rust
            val typeField = (jsonProperty.value as? JsonObject)?.findProperty("type") ?: return@mapNotNull null
            if (typeField.value?.text?.trim('"') != "rust") {
                return@mapNotNull null
            }

            // get the corresponding cargo package
            val packageField = (jsonProperty.value as? JsonObject)?.findProperty("package") ?: return@mapNotNull null
            val crateName = packageField.value?.text?.trim('"') ?: return@mapNotNull null
            val cargoPackage = allPackages.find { item -> crateName == item.name } ?: return@mapNotNull null

            // get the corresponding candid file
            val candidField = (jsonProperty.value as? JsonObject)?.findProperty("candid") ?: return@mapNotNull null
            val candidPath = candidField.value?.text?.trim('"') ?: return@mapNotNull null
            val candidVirtualFile = LocalFileSystem.getInstance()
                .findFileByPath(Paths.get(project.basePath, candidPath).toString()) ?: return@mapNotNull null

            RustCanister(project, cargoPackage, candidVirtualFile)
        }
    }

    fun updateCache() {
        cachedRustCanister.mapValues { getRustCanisters(it.key) }
    }

    private data class RustCanister(
        val project: Project,
        val cargoPackage: CargoWorkspace.Package,
        val candidFile: VirtualFile
    ) {
        fun getScope(): GlobalSearchScope {
            return GlobalSearchScopes.directoriesScope(project, true, cargoPackage.contentRoot)
        }
    }
}