package ru.uniyar.domain.storage

import org.ktorm.dsl.QueryRowSet
import ru.uniyar.domain.models.ProjectModel
import ru.uniyar.domain.models.UserModel
import ru.uniyar.domain.storage.tables.ProjectTable
import ru.uniyar.domain.storage.tables.UserTable

fun QueryRowSet.toUserModel() = UserModel(
    this[UserTable.id]!!,
    this[UserTable.firstName]!!,
    this[UserTable.lastName]!!,
)

fun QueryRowSet.toProjectModel() = ProjectModel(
    this[ProjectTable.id]!!,
    this.toUserModel(),
    this[ProjectTable.title]!!,
    this[ProjectTable.description]!!,
    this[ProjectTable.targetFundSize]!!,
    this[ProjectTable.startDate]!!,
    this[ProjectTable.endDate]!!
)