package ru.uniyar.domain.storage

import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.innerJoin
import org.ktorm.dsl.insert
import org.ktorm.dsl.map
import org.ktorm.dsl.select
import org.ktorm.dsl.update
import org.ktorm.dsl.where
import ru.uniyar.domain.models.ProjectModel
import ru.uniyar.domain.models.ProjectSponsorsModel
import ru.uniyar.domain.models.SponsorModel
import ru.uniyar.domain.models.projectId
import ru.uniyar.domain.storage.tables.ProjectTable
import ru.uniyar.domain.storage.tables.SponsorTable
import ru.uniyar.domain.storage.tables.UserTable
import ru.uniyar.dto.Project

open class ProjectManager(private val database: Database) : Storage<ProjectModel>() {
    override fun getAll(): List<ProjectModel> = database
        .from(ProjectTable).innerJoin(UserTable, UserTable.id eq ProjectTable.userId)
        .select(ProjectTable.columns + UserTable.columns)
        .map { it.toProjectModel() }

    fun createProject(project: Project) =
        database.insert(ProjectTable) {
            set(ProjectTable.userId, project.userId)
            set(ProjectTable.targetFundSize, project.targetFundSize)
            set(ProjectTable.title, project.title)
            set(ProjectTable.description, project.description)
            set(ProjectTable.startDate, project.startDate)
            set(ProjectTable.endDate, project.endDate)
        }

    fun updateProject(id: projectId, project: Project) =
        database.update(ProjectTable) {
            set(ProjectTable.userId, project.userId)
            set(ProjectTable.targetFundSize, project.targetFundSize)
            set(ProjectTable.title, project.title)
            set(ProjectTable.description, project.description)
            set(ProjectTable.startDate, project.startDate)
            set(ProjectTable.endDate, project.endDate)
            where {
                it.id eq id
            }
        }

    fun getProject(id: projectId) = database
        .from(ProjectTable).innerJoin(UserTable, UserTable.id eq ProjectTable.userId)
        .select(ProjectTable.columns + UserTable.columns)
        .where(ProjectTable.id eq id)
        .map { it.toProjectModel() }.firstOrNull()

    fun getSponsors(id: projectId) =
        database.from(SponsorTable).innerJoin(UserTable, SponsorTable.userId eq UserTable.id)
            .select(UserTable.columns + SponsorTable.columns)
            .where(SponsorTable.projectId eq id)
            .map {
                SponsorModel(it.toUserModel(), it[SponsorTable.sum]!!)
            }

    fun getProjectSponsors(id: projectId): ProjectSponsorsModel? {
        val project = getProject(id) ?: return null
        val sponsors = getSponsors(id)
        return ProjectSponsorsModel(
            project, sponsors
        )
    }


}
