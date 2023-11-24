package ru.uniyar.domain.models

import java.time.LocalDate

typealias projectId = Int

class ProjectModel(
    id: projectId,
    val user: UserModel,
    val title: String,
    val description: String,
    val targetFundSize: Long,
    val startDate: LocalDate,
    val endDate: LocalDate,
) : Model(id)
