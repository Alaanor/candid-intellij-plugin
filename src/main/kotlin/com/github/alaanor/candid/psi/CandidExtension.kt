package com.github.alaanor.candid.psi

fun CandidImportStatement.importPathString(): String? {
    return this.stringLiteral?.text?.trim('"')
}