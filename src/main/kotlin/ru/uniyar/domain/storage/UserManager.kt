package ru.uniyar.domain.storage

import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.map
import org.ktorm.dsl.mapNotNull
import org.ktorm.dsl.select
import org.ktorm.dsl.where
import ru.uniyar.domain.models.UserModel
import ru.uniyar.domain.models.userId
import ru.uniyar.domain.storage.tables.UserTable
import ru.uniyar.dto.User

open class UserManager(private val database: Database) {
    fun getAll(): List<UserModel> =
        database
            .from(UserTable).select(UserTable.columns)
            .map { it.toUserModel() }

    fun createUser(user: User) =
        database
            .insert(UserTable) {
                set(UserTable.firstName, user.firstName)
                set(UserTable.lastName, user.lastName)
            }

    fun getUser(id: userId) =
        database.from(UserTable)
            .select(UserTable.columns).where(UserTable.id eq id)
            .mapNotNull { it.toUserModel() }.firstOrNull()
}
