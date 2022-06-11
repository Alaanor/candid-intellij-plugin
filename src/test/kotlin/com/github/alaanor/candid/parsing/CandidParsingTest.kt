package com.github.alaanor.candid.parsing

import com.github.alaanor.candid.CandidParserDefinition
import com.intellij.testFramework.ParsingTestCase

class CandidParsingTest : ParsingTestCase("", "did", CandidParserDefinition()) {

    fun testCommonTestData() = doTest(true)
    fun testCommentTestData() = doTest(true)

    override fun getTestDataPath(): String = "src/test/resources/parsing"
    override fun skipSpaces(): Boolean = false
    override fun includeRanges(): Boolean = true
}