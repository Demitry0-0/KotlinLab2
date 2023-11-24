package ru.uniyar.domain.models

import java.time.LocalDate
import java.time.LocalDateTime

typealias projectId = Int

class ProjectModel(
    id: projectId,
    val userModel: UserModel,
    val title: String,
    val description: String,
    val targetFundSize: Long,
    val startDate: LocalDate,
    val endDate: LocalDate,
) : Model(id)
