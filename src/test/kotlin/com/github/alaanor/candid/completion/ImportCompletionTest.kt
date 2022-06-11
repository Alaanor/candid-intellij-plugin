package com.github.alaanor.candid.completion

import org.junit.Test

class ImportCompletionTest : CandidCompletionTestBase() {

    @Test
    fun `should find B did file`() =
        checkContainsCompletion(listOf("B.did"), "simple/A.did", "simple/B.did")

    @Test
    fun `should find B did file with relative path`() =
        checkContainsCompletion(listOf("../b/b/B.did"), "subfolder/a/A.did", "subfolder/b/b/B.did")
}