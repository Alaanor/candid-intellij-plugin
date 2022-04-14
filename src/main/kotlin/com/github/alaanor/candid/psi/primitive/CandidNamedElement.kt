package com.github.alaanor.candid.psi.primitive

import com.intellij.psi.PsiNameIdentifierOwner
import com.intellij.psi.PsiNamedElement

interface CandidNamedElement : PsiNamedElement
interface CandidNameIdentifierOwner : CandidNamedElement, PsiNameIdentifierOwner