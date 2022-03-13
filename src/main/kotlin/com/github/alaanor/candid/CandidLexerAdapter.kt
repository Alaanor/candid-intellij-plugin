package com.github.alaanor.candid

import com.github.alaanor.candid.lexer.CandidLexer
import com.intellij.lexer.FlexAdapter

class CandidLexerAdapter : FlexAdapter(CandidLexer(null))