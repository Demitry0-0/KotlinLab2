package ru.uniyar.domain.operations

import ru.uniyar.domain.models.ProjectModel
import ru.uniyar.domain.models.projectId
import ru.uniyar.domain.storage.ProjectManager
import ru.uniyar.dto.Project
import ru.uniyar.dto.ProjectSponsors
import kotlin.math.max

class ProjectService(
    val projectManager: ProjectManager
) {
    fun createProject(project: Project): Int = projectManager.createProject(project)
    fun updateProject(id: projectId, project: Project) = projectManager.updateProject(id, project)
    fun getProject(id: projectId) = projectManager.getProject(id)

    fun getProjectSponsors(id: projectId): ProjectSponsors? {
        val project = projectManager.getProject(id) ?: return null
        val sponsors = projectManager.getSponsors(id)
        val sumSponsors = sponsors.sumOf { it.sum }
        return ProjectSponsors(
            project,
            sponsors,
            max(project.targetFundSize - sumSponsors, 0),
            sumSponsors
        )
    }

    fun getAllProjects() = projectManager.getAll()
}