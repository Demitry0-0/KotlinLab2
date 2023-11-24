package ru.uniyar.domain.operations

import ru.uniyar.domain.models.UserModel
import ru.uniyar.domain.models.userId
import ru.uniyar.domain.storage.UserManager
import ru.uniyar.dto.User

class UserService(val manager: UserManager) {
    fun createUser(user: User) = manager.createUser(user)

    fun getUser(id: userId) = manager.getUser(id)

    fun getAllUsers(): List<UserModel> = manager.getAll()
}
