package ru.uniyar.web.models

import org.http4k.template.ViewModel
import ru.uniyar.domain.models.ProjectModel
import ru.uniyar.domain.models.SponsorModel

class ProjectPageViewModel(
    val project: ProjectModel,
    val sponsors: List<SponsorModel>,
    val leftSum: Long,
    val totalSum: Long,
) : ViewModel
