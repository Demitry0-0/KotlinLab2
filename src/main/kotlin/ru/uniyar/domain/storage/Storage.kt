package ru.uniyar.domain.storage

import ru.uniyar.domain.models.ModelId

abstract class Storage<T> {
    private val storage: MutableList<T> = mutableListOf()

    open fun add(data: T) = storage.add(data)

    open fun get(id: ModelId) = storage.getOrNull(id)

    open fun getAll(): List<T> = storage

    open fun fill(data: List<T>) = storage.addAll(data)
}
