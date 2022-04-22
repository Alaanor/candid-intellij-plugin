package com.github.alaanor.candid.reference

import com.github.alaanor.candid.CandidFileType
import com.github.alaanor.candid.psi.CandidImportStatement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.icons.AllIcons
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiManager
import com.intellij.psi.PsiReferenceBase
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.util.indexing.FileBasedIndex
import org.intellij.markdown.flavours.gfm.table.GitHubTableMarkerProvider.Companion.contains
import java.nio.file.Paths

class CandidImportReference(importStatement: CandidImportStatement, textRange: TextRange) :
    PsiReferenceBase<CandidImportStatement>(importStatement, textRange) {

    private val targetName: String get() = element.stringLiteral!!.text.trim('"')

    override fun resolve(): PsiElement? {
        if (element.stringLiteral == null) {
            return null
        }

        val target = element.containingFile.virtualFile.findFileByRelativePath("../${this.targetName}")
        return target?.let { PsiManager.getInstance(element.project).findFile(it) }
    }

    override fun getVariants(): Array<Any> {
        val files = FileBasedIndex.getInstance().getContainingFiles(
            FileTypeIndex.NAME,
            CandidFileType.INSTANCE,
            GlobalSearchScope.projectScope(element.project)
        )

        val currentPath = Paths.get(element.containingFile.originalFile.virtualFile.parent.path)
        return files.map { file ->
            val relativePath = currentPath.relativize(Paths.get(file.path))
            val contextText = if (relativePath.toString().contains('/')) "($relativePath)" else ""
            LookupElementBuilder.create(relativePath)
                .withPresentableText(file.name)
                .withTypeText(contextText)
                .withIcon(AllIcons.FileTypes.Any_type)
                .withCaseSensitivity(false)
                .withInsertHandler { context, _ ->
                    context.document.replaceString(
                        context.editor.caretModel.offset,
                        context.editor.document.text.indexOf('"', context.editor.caretModel.offset),
                        ""
                    );
                }
        }.toTypedArray()
    }

}