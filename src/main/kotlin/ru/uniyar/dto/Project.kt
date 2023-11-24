package ru.uniyar.dto

import java.time.LocalDate

data class Project(
    val title: String,
    val userId: Int,
    val description: String,
    val targetFundSize: Long,
    val startDate: LocalDate,
    val endDate: LocalDate,
)
