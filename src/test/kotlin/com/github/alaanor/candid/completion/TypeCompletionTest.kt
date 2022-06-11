package com.github.alaanor.candid.completion

import org.junit.Test

class TypeCompletionTest : CandidCompletionTestBase() {

    private val primitiveType = listOf(
        "nat", "nat8", "nat16", "nat32", "nat64",
        "int", "int8", "int16", "int32", "int64",
        "float32", "float64", "bool", "text",
        "null", "reserved", "empty", "principal",
        "record", "variant", "func", "service",
        "blob", "opt", "vec"
    )

    @Test
    fun `should contains all the primitive type`() =
        checkContainsCompletion(primitiveType, "type foo = <caret>")

    @Test
    fun `should not suggest anything after a common type`() =
        checkContainsCompletion(emptyList(), "type foo = nat <caret>")

    @Test
    fun `should continue to suggest after a vec`() =
        checkContainsCompletion(primitiveType, "type foo = vec <caret>")

    @Test
    fun `should continue to suggest after an opt`() =
        checkContainsCompletion(primitiveType, "type foo = opt <caret>")

    @Test
    fun `should continue to suggest after an opt vec`() =
        checkContainsCompletion(primitiveType, "type foo = opt vec <caret>")

    @Test
    fun `should not suggest anything outside`() =
        checkContainsCompletion(emptyList(), "type foo = nat; <caret>")

    @Test
    fun `should find foo type`() =
        checkContainsCompletion(listOf("foo"), "type foo = nat; type bar = <caret>")

    @Test
    fun `should be able to find the same declared type in the definition`() =
        checkContainsCompletion(listOf("foo"), "type foo = <caret>")

    // Record

    @Test
    fun `should get completion in a record field`() =
        checkContainsCompletion(primitiveType, "type foo = record { field: <caret> }")

    @Test
    fun `should get completion in a record field even without field name`() =
        checkContainsCompletion(primitiveType, "type foo = record { <caret> }")

    // Variant

    @Test
    fun `should get completion in a variant field`() =
        checkContainsCompletion(primitiveType, "type foo = variant { field: <caret> }")
}