package com.github.tjdevries.jupyterascendingpycharm.services

import com.intellij.openapi.project.Project
import com.github.tjdevries.jupyterascendingpycharm.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
