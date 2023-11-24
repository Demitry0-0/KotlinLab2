package ru.uniyar.domain.storage

import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.innerJoin
import org.ktorm.dsl.insert
import org.ktorm.dsl.map
import org.ktorm.dsl.select
import ru.uniyar.domain.models.ProjectModel
import ru.uniyar.domain.models.UserModel
import ru.uniyar.domain.storage.tables.ProjectTable
import ru.uniyar.domain.storage.tables.UserTable
import ru.uniyar.dto.Project

open class ProjectManager(private val database: Database) : Storage<ProjectModel>() {
    override fun getAll(): List<ProjectModel> = database
        .from(ProjectTable).innerJoin(UserTable, UserTable.id eq ProjectTable.id)
        .select(ProjectTable.columns)
        .map { row ->
            ProjectModel(
                row[ProjectTable.id]!!,
                UserModel(
                    row[UserTable.id]!!,
                    row[UserTable.firstName]!!,
                    row[UserTable.lastName]!!,
                ),
                row[ProjectTable.title]!!,
                row[ProjectTable.description]!!,
                row[ProjectTable.targetFundSize]!!,
                row[ProjectTable.startDate]!!,
                row[ProjectTable.endDate]!!
            )

        }

    fun createProject(project: Project) =
        database.insert(ProjectTable) {
            set(ProjectTable.userId, project.userId)
            set(ProjectTable.targetFundSize, project.targetFundSize)
            set(ProjectTable.title, project.title)
            set(ProjectTable.description, project.description)
            set(ProjectTable.startDate, project.startDate)
            set(ProjectTable.endDate, project.endDate)
        }

}
