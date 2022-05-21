package com.github.alaanor.candid.util

import com.github.alaanor.candid.psi.*
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.psi.impl.include.FileIncludeManager
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.SearchScope
import com.intellij.psi.util.PsiTreeUtil

object CandidImportUtil {

    fun getAllImportedFileFor(psiFile: PsiFile): Set<PsiFile> {
        return getAllImportedFileTreeFor(psiFile).toSet()
    }

    private fun getAllImportedFileTreeFor(
        psiFile: PsiFile,
        tree: TreeSet<PsiFile> = TreeSet(psiFile, psiFileEquality)
    ): TreeSet<PsiFile> {
        PsiTreeUtil.findChildrenOfType(psiFile, CandidImportStatement::class.java).forEach { importStatement ->
            val importPath = importStatement.importPathString() ?: return@forEach
            val file = resolveRelatively(psiFile, importPath) ?: return@forEach
            if (file.filePath() == psiFile.filePath()) return@forEach

            if (tree.addChildIfNonExistent(file)) {
                tree.getChildren().forEach {
                    getAllImportedFileTreeFor(file, it)
                }
            }
        }

        return tree
    }

    fun resolveRelatively(psiFile: PsiFile, importPath: String): PsiFile? {
        val target = psiFile.originalFile.virtualFile.findFileByRelativePath("../${importPath}")
        return target?.let { PsiManager.getInstance(psiFile.project).findFile(it) }
    }

    fun getAllUnusedImportFor(file: PsiFile): Collection<CandidImportStatement> {
        val unusedImport = mutableSetOf<CandidImportStatement>()

        // 1. We first look from the current file to all its child (imports)

        val allRequiredFiles = PsiTreeUtil.findChildrenOfType(file, CandidIdentifierReference::class.java).mapNotNull {
            it.reference?.resolve()?.containingFile?.originalFile
        }.filter { it.filePath() != file.filePath() }

        // iterate over all possible candidate to mark as unused
        PsiTreeUtil.findChildrenOfType(file, CandidImportStatement::class.java).forEach { importStatement ->
            val importTree = importStatement.importedPsiFile()?.let { getAllImportedFileTreeFor(it) } ?: return@forEach

            if (!allRequiredFiles.any { importTree.exists(it) }) {
                unusedImport.add(importStatement)
            }
        }

        // 2. Then a second pass where we check if there's a used import from a parent file which would
        // invalidate the unused case

        val parentFiles = getAllUsageOf(file)
        val unusedImportPathTrees = unusedImport.mapNotNull { importStatement ->
            val tree = importStatement.importedPsiFile()?.let { file -> getAllImportedFileTreeFor(file) }
            return@mapNotNull if (tree == null) null else Pair(importStatement, tree)
        }

        parentFiles.forEach { parentFile ->
            if (unusedImport.isEmpty()) return@forEach

            PsiTreeUtil.findChildrenOfType(parentFile, CandidIdentifierReference::class.java).mapNotNull { reference ->
                if (unusedImport.isEmpty()) return@forEach

                val requiredPath = reference.reference?.resolve()?.containingFile?.originalFile ?: return@forEach
                val occurrence = unusedImportPathTrees.find { pair -> pair.second.exists(requiredPath) }

                if (occurrence != null) {
                    // now we know a parent is using this path, which invalid the case
                    unusedImport.remove(occurrence.first)
                }
            }
        }

        return unusedImport
    }

    // Find all files where there's an indirect or direct import to the current file
    // So finding all the parents import wise
    private fun getAllUsageOf(psiFile: PsiFile, fileSet: MutableSet<PsiFile> = mutableSetOf()): Set<PsiFile> {
        val psiManager = PsiManager.getInstance(psiFile.project)
        FileIncludeManager
            .getManager(psiFile.project)
            .getIncludingFiles(psiFile.virtualFile, false)
            .forEach { virtualFile ->
                val file = psiManager.findFile(virtualFile) ?: return@forEach
                if (!fileSet.contains(file)) {
                    fileSet.add(file)
                    getAllUsageOf(file, fileSet)
                }
            }

        return fileSet
    }

    fun getImportedSearchScope(file: PsiFile) : GlobalSearchScope {
        val importedFiles = getAllImportedFileFor(file).toMutableSet().map { it.virtualFile }
        return GlobalSearchScope.filesScope(file.project, importedFiles)
    }
}

interface Equality<T> {
    fun equals(a: T, b: T): Boolean
}

private val psiFileEquality = object : Equality<PsiFile> {
    override fun equals(a: PsiFile, b: PsiFile): Boolean = a.filePath() == b.filePath()
}

private class TreeSet<T> {
    private val data: T
    private val root: TreeSet<T>
    private val parent: TreeSet<T>?
    private val children: MutableList<TreeSet<T>>
    private val equality: Equality<T>

    constructor(data: T, equality: Equality<T>) : this(data, null, null, mutableListOf(), equality)

    constructor(
        data: T,
        root: TreeSet<T>?,
        parent: TreeSet<T>?,
        children: MutableList<TreeSet<T>>,
        equality: Equality<T>
    ) {
        this.data = data
        this.root = root ?: this
        this.parent = parent
        this.children = children
        this.equality = equality
    }

    fun getChildren(): List<TreeSet<T>> = children.toList()
    fun exists(element: T): Boolean = root.get(element) != null

    fun get(element: T): TreeSet<T>? {
        if (equality.equals(element, data)) {
            return this
        }

        return children.find { it.get(element) != null }
    }

    fun addChildIfNonExistent(element: T): Boolean {
        if (exists(element)) {
            return false
        }

        this.children.add(TreeSet(element, root, this, mutableListOf(), equality))
        return true
    }

    fun toSet(): Set<T> {
        val result = mutableSetOf<T>()
        result.add(data)
        result.addAll(getChildren().flatMap { it.toSet() })
        return result
    }

    companion object {
        @Suppress("unused") // cool for debug purpose
        fun print(tree: TreeSet<PsiFile>, level: Int = 0) {
            println("-".repeat(level) + " ${tree.data.filePath()}")
            tree.children.forEach { print(it, level + 1) }
        }
    }
}