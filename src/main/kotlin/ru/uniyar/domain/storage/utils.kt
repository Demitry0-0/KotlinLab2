package ru.uniyar.domain.storage

import org.ktorm.dsl.QueryRowSet
import ru.uniyar.domain.models.UserModel
import ru.uniyar.domain.storage.tables.UserTable

fun QueryRowSet.toUserModel() = UserModel(
    this[UserTable.id]!!,
    this[UserTable.firstName]!!,
    this[UserTable.lastName]!!,
)