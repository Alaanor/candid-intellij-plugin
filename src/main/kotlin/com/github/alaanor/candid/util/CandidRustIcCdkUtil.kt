package com.github.alaanor.candid.util

import org.rust.lang.core.psi.RsFunction
import org.rust.lang.core.psi.ext.containingCrate
import org.rust.lang.core.psi.ext.name

class CandidRustIcCdkUtil {
    companion object {
        private val metaName = arrayOf("update", "query")

        fun getName(rsFunction: RsFunction): String? {
            val icCdkMetaItem = rsFunction.rawMetaItems.find {
                metaName.contains(it.name) && it.path
                    ?.reference
                    ?.resolve()
                    ?.containingCrate
                    ?.normName == "ic_cdk_macros"
            } ?: return null

            return icCdkMetaItem.metaItemArgsList.find { it.name == "name" }?.value
                ?: rsFunction.name
        }
    }
}