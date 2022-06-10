package com.github.alaanor.candid.completion

import com.intellij.codeInsight.completion.CompletionType
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixture4TestCase

abstract class CandidCompletionTestBase : LightPlatformCodeInsightFixture4TestCase() {
    protected fun checkContainsCompletion(expected: Collection<String>, code: String) {
        inlineFile(code)
        myFixture.complete(CompletionType.BASIC)
        assertNotNull(myFixture.lookupElementStrings)
        assertSameElements(myFixture.lookupElementStrings!!, expected)
    }

    private fun inlineFile(code: String, name: String = "test.did") {
        myFixture.configureByText(name, code.replace("/*caret*/", "<caret>"))
    }
}