package com.github.alaanor.candid.util

import org.rust.ide.refactoring.move.common.RsMoveUtil.textNormalized
import org.rust.lang.core.psi.RsFunction
import org.rust.lang.core.psi.RsMetaItem
import org.rust.lang.core.psi.ext.containingCrate
import org.rust.lang.core.psi.ext.name

class CandidRustIcCdkUtil {
    companion object {
        fun getName(rsFunction: RsFunction): String? {
            val rsMetaItem = getMetaItem(rsFunction) ?: return null
            return getName(rsMetaItem, rsFunction)
        }

        private fun getName(rsMetaItem: RsMetaItem, rsFunction: RsFunction): String? {
            return rsMetaItem.metaItemArgsList.find { it.name == "name" }?.value
                ?: rsFunction.name
        }

        private fun getMetaItem(rsFunction: RsFunction): RsMetaItem? {
            return rsFunction.rawMetaItems.find {
                when (it.path?.textNormalized) {
                    "ic_cdk_macros::update", "ic_cdk_macros::query" -> true
                    "update", "query" -> {
                        it.path
                            ?.reference
                            ?.resolve()
                            ?.containingCrate
                            ?.normName == "ic_cdk_macros"
                    }
                    else -> false
                }
            }
        }
    }
}