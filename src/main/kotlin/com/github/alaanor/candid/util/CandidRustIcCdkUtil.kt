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

        fun getName(rsMetaItem: RsMetaItem): String? {
            return rsMetaItem.metaItemArgsList.find { it.name == "name" }?.value
        }

        fun isIcCdkUpdateQuery(rsMetaItem: RsMetaItem): Boolean {
            return when (rsMetaItem.path?.textNormalized) {
                "ic_cdk_macros::update", "ic_cdk_macros::query" -> true
                "update", "query" -> {
                    rsMetaItem.path
                        ?.reference
                        ?.resolve()
                        ?.containingCrate
                        ?.normName == "ic_cdk_macros"
                }
                else -> false
            }
        }

        private fun getName(rsMetaItem: RsMetaItem, rsFunction: RsFunction): String? {
            return getName(rsMetaItem) ?: rsFunction.name
        }

        private fun getMetaItem(rsFunction: RsFunction): RsMetaItem? {
            return rsFunction.rawMetaItems.find { isIcCdkUpdateQuery(it) }
        }
    }
}