package ru.uniyar.dto

import ru.uniyar.domain.models.ProjectModel
import ru.uniyar.domain.models.SponsorModel

data class ProjectSponsors(
    val project: ProjectModel,
    val sponsors: List<SponsorModel>,
    val leftSum: Long,
    val totalSum: Long,
)
