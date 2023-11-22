package ru.uniyar.domain.models

typealias userId = ModelId

class User(
    id: userId,
    val firstName: String,
    val lastName: String,
) : Model(id)
