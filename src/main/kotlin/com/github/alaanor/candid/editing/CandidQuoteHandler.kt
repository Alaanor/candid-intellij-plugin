package com.github.alaanor.candid.editing

import com.github.alaanor.candid.CandidTypes
import com.intellij.codeInsight.editorActions.SimpleTokenSetQuoteHandler

class CandidQuoteHandler : SimpleTokenSetQuoteHandler(CandidTypes.DOUBLE_QUOTE)