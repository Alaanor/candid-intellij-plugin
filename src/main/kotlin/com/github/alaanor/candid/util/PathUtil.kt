package com.github.alaanor.candid.util

import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiElement
import java.nio.file.Paths

fun PsiElement.filePath(): String = this.containingFile.originalFile.virtualFile.path
fun PsiElement.filePathDirectory(): String = this.containingFile.originalFile.virtualFile.parent.path

fun PsiElement.getRelativePath(psiElement: PsiElement): String {
    return Paths.get(this.filePathDirectory())
        .relativize(Paths.get(psiElement.filePath()))
        .toString()
}

fun PsiElement.getRelativePath(virtualFile: VirtualFile): String {
    return Paths.get(this.filePathDirectory())
        .relativize(Paths.get(virtualFile.path))
        .toString()
}

