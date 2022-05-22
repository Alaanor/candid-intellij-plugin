package com.github.alaanor.candid.icon

import com.intellij.openapi.util.IconLoader

object CandidIcons {
    @JvmStatic
    val FileType = IconLoader.getIcon("/icon/fileType.svg", javaClass)

    @JvmStatic
    val Type = IconLoader.getIcon("/icon/type.svg", javaClass)

    @JvmStatic
    val Import = IconLoader.getIcon("/icon/import.svg", javaClass)

    @JvmStatic
    val Service = IconLoader.getIcon("/icon/service.svg", javaClass)

    @JvmStatic
    val MethodQuery = IconLoader.getIcon("/icon/method_query.svg", javaClass)

    @JvmStatic
    val MethodUpdate = IconLoader.getIcon("/icon/method_update.svg", javaClass)

    @JvmStatic
    val MethodOneway = IconLoader.getIcon("/icon/method_oneway.svg", javaClass)
}