package ru.uniyar.web.models

import org.http4k.template.ViewModel
import ru.uniyar.domain.models.User

class ProjectRegistrationViewModel(
    val users: List<User> = emptyList(),
    val project: Map<String, Any?> = emptyMap()
) : ViewModel
