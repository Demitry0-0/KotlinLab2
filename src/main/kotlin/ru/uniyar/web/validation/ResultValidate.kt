package ru.uniyar.web.validation

data class ResultValidate<R>(
    val value: R?,
    val errors: List<String>
)