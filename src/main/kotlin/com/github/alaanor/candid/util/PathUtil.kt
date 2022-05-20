package com.github.alaanor.candid.util

import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiElement
import java.nio.file.Paths

fun PsiElement.filePath(): String = containingFile.originalFile.virtualFile.path
fun PsiElement.filePathDirectory(): String = containingFile.originalFile.virtualFile.parent.path

fun PsiElement.projectFilePath(): String {
    return Paths.get(project.basePath)
        .relativize(Paths.get(containingFile.originalFile.virtualFile.path))
        .toString()
}

fun PsiElement.getRelativePath(psiElement: PsiElement): String {
    return Paths.get(filePathDirectory())
        .relativize(Paths.get(psiElement.filePath()))
        .toString()
}

fun PsiElement.getRelativePath(virtualFile: VirtualFile): String {
    return Paths.get(filePathDirectory())
        .relativize(Paths.get(virtualFile.path))
        .toString()
}

