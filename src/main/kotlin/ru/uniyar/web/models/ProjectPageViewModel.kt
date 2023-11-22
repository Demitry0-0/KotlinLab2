package ru.uniyar.web.models

import org.http4k.template.ViewModel
import ru.uniyar.domain.models.Project

class ProjectPageViewModel(
    val project: Project,
    val leftSum: Long,
    val totalSum: Long,
) : ViewModel
