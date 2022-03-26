package com.github.alaanor.candid

import com.intellij.testFramework.ParsingTestCase

class CandidParsingTest : ParsingTestCase("", "did", CandidParserDefinition()) {

    fun testCommonTestData() = doTest(true)
    fun testCommentTestData() = doTest(true)

    override fun getTestDataPath(): String = "src/test/resources"
    override fun skipSpaces(): Boolean = false
    override fun includeRanges(): Boolean = true
}