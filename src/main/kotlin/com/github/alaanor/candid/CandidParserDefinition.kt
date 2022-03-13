package com.github.alaanor.candid

import com.github.alaanor.candid.psi.CandidFile
import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet

class CandidParserDefinition : ParserDefinition {

    companion object {
        val FILE = IFileElementType(CandidLanguage.INSTANCE)
    }

    override fun createLexer(project: Project?): Lexer {
        return CandidLexerAdapter()
    }

    override fun createParser(project: Project?): PsiParser {
        return CandidParser()
    }

    override fun getFileNodeType(): IFileElementType {
        return FILE
    }

    override fun getCommentTokens(): TokenSet {
        return TokenSet.EMPTY
    }

    override fun getStringLiteralElements(): TokenSet {
        return TokenSet.EMPTY
    }

    override fun createElement(node: ASTNode?): PsiElement {
        return CandidTypes.Factory.createElement(node)
    }

    override fun createFile(viewProvider: FileViewProvider): PsiFile {
        return CandidFile(viewProvider)
    }
}