package ru.uniyar.web.models

import org.http4k.template.ViewModel
import ru.uniyar.domain.models.ProjectModel
import ru.uniyar.domain.models.UserModel

class ProjectChangedViewModel(
    val users: List<UserModel> = emptyList(),
    val project: ProjectModel,
    val messages: List<String> = emptyList()
) : ViewModel