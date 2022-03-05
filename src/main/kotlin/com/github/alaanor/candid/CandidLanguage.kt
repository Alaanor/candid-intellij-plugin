package com.github.alaanor.candid

import com.intellij.lang.Language

class CandidLanguage private constructor() : Language("Candid") {
    companion object {
        val INSTANCE = CandidLanguage()
    }
}