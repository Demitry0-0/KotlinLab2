package ru.uniyar.web.models

import org.http4k.template.ViewModel
import ru.uniyar.domain.models.ProjectModel

class ProjectPageViewModel(
    val projectModel: ProjectModel,
    val leftSum: Long,
    val totalSum: Long,
) : ViewModel
