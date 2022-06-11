package com.github.alaanor.candid.completion

import com.intellij.codeInsight.completion.CompletionType
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixture4TestCase

abstract class CandidCompletionTestBase : LightPlatformCodeInsightFixture4TestCase() {

    override fun getTestDataPath(): String = "src/test/resources/completion"

    protected fun checkContainsCompletion(expected: Collection<String>, code: String) {
        inlineFile(code)
        myFixture.complete(CompletionType.BASIC)
        assertNotNull(myFixture.lookupElementStrings)
        assertSameElements(myFixture.lookupElementStrings!!, expected)
    }

    protected fun checkContainsCompletion(expected: Collection<String>, vararg files: String) {
        myFixture.configureByFiles(*files)
        myFixture.complete(CompletionType.BASIC)
        assertNotNull(myFixture.lookupElementStrings)
        assertSameElements(myFixture.lookupElementStrings!!, expected)
    }

    private fun inlineFile(code: String, name: String = "test.did") {
        myFixture.configureByText(name, code)
    }
}