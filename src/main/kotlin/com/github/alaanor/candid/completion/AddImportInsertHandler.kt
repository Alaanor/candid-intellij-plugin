package com.github.alaanor.candid.completion

import com.github.alaanor.candid.psi.CandidFile
import com.github.alaanor.candid.psi.CandidIdentifierDeclaration
import com.intellij.codeInsight.completion.InsertHandler
import com.intellij.codeInsight.completion.InsertionContext
import com.intellij.codeInsight.lookup.LookupElement

class AddImportInsertHandler(private val identifier: CandidIdentifierDeclaration): InsertHandler<LookupElement> {
    override fun handleInsert(context: InsertionContext, item: LookupElement) {
        val file = context.file as? CandidFile ?: return
        file.addImportFor(identifier)
    }
}