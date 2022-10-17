package com.github.alaanor.candid.util

import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiElement
import java.nio.file.Paths

fun PsiElement.filePath(): String = containingFile.originalFile.virtualFile.path
fun PsiElement.filePathDirectory(): String = containingFile.originalFile.virtualFile.parent.path

fun PsiElement.projectFilePath(): String? {
    val from = Paths.get(project.basePath)
    val to = Paths.get(containingFile.originalFile.virtualFile.path)
    if (from.root != to.root)
        return null
    return from.relativize(to).toString()
}

fun PsiElement.getRelativePath(psiElement: PsiElement): String? {
    val from = Paths.get(filePathDirectory())
    val to = Paths.get(psiElement.filePath())
    if (from.root != to.root)
        return null
    return from.relativize(to).toString()
}

fun PsiElement.getRelativePath(virtualFile: VirtualFile): String? {
    val from = Paths.get(filePathDirectory())
    val to = Paths.get(virtualFile.path)
    if (from.root != to.root)
        return null
    return from.relativize(to).toString()
}

