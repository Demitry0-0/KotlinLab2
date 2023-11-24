package ru.uniyar.web.models

import org.http4k.template.ViewModel
import ru.uniyar.domain.models.UserModel

class ProjectRegistrationViewModel(
    val users: List<UserModel> = emptyList(),
    val project: Map<String, Any?> = emptyMap()
) : ViewModel
