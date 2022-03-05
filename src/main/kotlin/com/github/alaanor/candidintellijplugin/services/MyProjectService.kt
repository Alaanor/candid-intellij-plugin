package com.github.alaanor.candidintellijplugin.services

import com.intellij.openapi.project.Project
import com.github.alaanor.candidintellijplugin.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
