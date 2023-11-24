package ru.uniyar.domain.models

typealias userId = ModelId

class UserModel(
    id: userId,
    val firstName: String,
    val lastName: String,
) : Model(id)
