package com.github.alaanor.candid.psi.stub

import com.github.alaanor.candid.CandidLanguage
import com.github.alaanor.candid.psi.CandidFile
import com.intellij.psi.stubs.PsiFileStub
import com.intellij.psi.tree.IStubFileElementType

interface CandidFileStub : PsiFileStub<CandidFile>, CandidStub<CandidFile> {
    object Type : IStubFileElementType<CandidFileStub>("CANDID_FILE", CandidLanguage.INSTANCE) {
        override fun getStubVersion(): Int {
            return 1;
        }

        override fun getExternalId(): String {
            return "candid.file"
        }
    }
}