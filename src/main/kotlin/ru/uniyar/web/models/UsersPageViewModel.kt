package ru.uniyar.web.models

import org.http4k.template.ViewModel
import ru.uniyar.domain.models.UserModel

class UsersPageViewModel(val users: List<UserModel>) : ViewModel
