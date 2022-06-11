package com.github.alaanor.candid.completion

import org.junit.Test

class TopLevelKeywordCompletionTest : CandidCompletionTestBase() {

    private val topLevelKeyword = listOf("type", "service", "import")

    @Test
    fun `should appear in an empty file`() =
        checkContainsCompletion(topLevelKeyword, "<caret>")

    @Test
    fun `should appear after a type`() =
        checkContainsCompletion(topLevelKeyword, "type Foobar = nat; <caret>")

    @Test
    fun `should appear after an import`() =
        checkContainsCompletion(topLevelKeyword, """import "non-existent-file.did"; <caret>""")

    @Test
    fun `should not appear after a type keyword`() =
        checkContainsCompletion(emptyList(), "type <caret>")

    @Test
    fun `should not appear after a service`() =
        checkContainsCompletion(emptyList(), "service : {} <caret>")
}
