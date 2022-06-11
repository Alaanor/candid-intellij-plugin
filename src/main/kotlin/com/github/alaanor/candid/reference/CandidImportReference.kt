package com.github.alaanor.candid.reference

import com.github.alaanor.candid.CandidFileType
import com.github.alaanor.candid.icon.CandidIcons
import com.github.alaanor.candid.psi.CandidImportStatement
import com.github.alaanor.candid.psi.importPathString
import com.github.alaanor.candid.util.CandidImportUtil
import com.github.alaanor.candid.util.filePath
import com.github.alaanor.candid.util.getRelativePath
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReferenceBase
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.refactoring.suggested.startOffset

class CandidImportReference(importStatement: CandidImportStatement, private var textRange: TextRange) :
    PsiReferenceBase<CandidImportStatement>(importStatement, textRange) {

    override fun resolve(): PsiElement? {
        val importPath = element.importPathString()
        if (element.stringLiteral == null || importPath == null) {
            return null
        }

        return CandidImportUtil.resolveRelatively(element.containingFile, importPath)
    }

    override fun getAbsoluteRange(): TextRange = textRange
    override fun getRangeInElement(): TextRange {
        return element.stringLiteral!!.textRange
            .shiftLeft(element.startOffset - 1)
    }

    override fun getVariants(): Array<LookupElementBuilder> {
        val files = FileTypeIndex.getFiles(CandidFileType.INSTANCE, GlobalSearchScope.projectScope(element.project))
        return files.mapNotNull { file ->
            if (file.path == element.filePath()) {
                return@mapNotNull null
            }

            val relativePath = element.getRelativePath(file)
            val contextText = if (relativePath.contains('/')) "($relativePath)" else ""
            LookupElementBuilder.create(relativePath)
                .withPresentableText(file.name)
                .withTypeText(contextText)
                .withIcon(CandidIcons.FileType)
                .withInsertHandler { context, _ ->
                    context.document.replaceString(
                        context.editor.caretModel.offset,
                        context.editor.document.text.indexOf('"', context.editor.caretModel.offset),
                        ""
                    )
                }
        }.toTypedArray()
    }

}