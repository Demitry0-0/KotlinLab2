package ru.uniyar.domain.operations

import ru.uniyar.domain.storage.ProjectManager
import ru.uniyar.dto.Project

class ProjectService(
    val projectManager: ProjectManager
) {
    fun createProject(project: Project): Int {
        return projectManager.createProject(project)
    }

    fun getAllProjects() = projectManager.getAll()
}