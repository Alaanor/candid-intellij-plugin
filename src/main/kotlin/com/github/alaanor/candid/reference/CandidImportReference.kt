package com.github.alaanor.candid.reference

import com.github.alaanor.candid.CandidFileType
import com.github.alaanor.candid.icon.CandidIcons
import com.github.alaanor.candid.psi.CandidImportStatement
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
import org.intellij.markdown.flavours.gfm.table.GitHubTableMarkerProvider.Companion.contains

class CandidImportReference(importStatement: CandidImportStatement, private var textRange: TextRange) :
    PsiReferenceBase<CandidImportStatement>(importStatement, textRange) {

    private val targetName: String get() = element.stringLiteral!!.text.trim('"')

    override fun resolve(): PsiElement? {
        if (element.stringLiteral == null) {
            return null
        }

        return CandidImportUtil.resolveRelatively(element.containingFile, this.targetName)
    }

    override fun getAbsoluteRange(): TextRange = textRange
    override fun getRangeInElement(): TextRange {
        return element.stringLiteral!!.textRange
            .shiftLeft(element.startOffset)
    }

    override fun getVariants(): Array<Any> {
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