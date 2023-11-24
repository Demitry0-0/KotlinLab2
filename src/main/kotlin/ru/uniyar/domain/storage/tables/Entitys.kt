package ru.uniyar.domain.storage.entitys

import org.ktorm.entity.Entity
import ru.uniyar.domain.models.UserModel
import java.time.LocalDate
import kotlin.reflect.KClass

interface UserEntity : Entity<UserEntity> {
    companion object : Entity.Factory<UserEntity>()

    val id: Int
    var firstName: String
    var lastName: String
    var deletedAt: LocalDate
}

interface ProjectEntity : Entity<ProjectEntity> {
    val id: Int
    val title: String
    val description: String
    val targetFundSize: Long
    val startDate: LocalDate
    val endDate: LocalDate
    val user: UserEntity
    var deletedAt: LocalDate
}
