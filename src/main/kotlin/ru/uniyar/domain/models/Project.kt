package ru.uniyar.domain.models

import java.time.LocalDateTime
typealias projectId = Int

class Project(
    id: projectId,
    val userModel: UserModel,
    val title: String,
    val description: String,
    val targetFundSize: Long,
    val sponsors: List<Pair<UserModel, Long>>,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val createdAt: LocalDateTime = LocalDateTime.now(),
) : Model(id)
