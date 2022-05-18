package com.github.alaanor.candid.index

import com.github.alaanor.candid.CandidFileType
import com.github.alaanor.candid.CandidLanguage
import com.github.alaanor.candid.psi.CandidFile
import com.github.alaanor.candid.psi.CandidImportStatement
import com.github.alaanor.candid.psi.importPathString
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.fileTypes.FileTypeRegistry
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.impl.include.FileIncludeInfo
import com.intellij.psi.impl.include.FileIncludeProvider
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.Consumer
import com.intellij.util.indexing.FileContent

class CandidFileIncludeProvider : FileIncludeProvider() {
    override fun getId(): String {
        return CandidLanguage.INSTANCE.id
    }

    override fun acceptFile(file: VirtualFile): Boolean {
        return FileTypeRegistry.getInstance().isFileOfType(file, CandidFileType.INSTANCE)
    }

    override fun registerFileTypesUsedForIndexing(fileTypeSink: Consumer<in FileType>) {
        fileTypeSink.consume(CandidFileType.INSTANCE)
    }

    override fun getIncludeInfos(content: FileContent): Array<FileIncludeInfo> {
        val file = content.psiFile
        if (file !is CandidFile) {
            return emptyArray()
        }

        return PsiTreeUtil.findChildrenOfType(file, CandidImportStatement::class.java).mapNotNull { import ->
            import.importPathString()?.let {
                FileIncludeInfo(it)
            }
        }.toTypedArray()
    }
}