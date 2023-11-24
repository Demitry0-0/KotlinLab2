package ru.uniyar

import org.http4k.template.PebbleTemplates
import ru.uniyar.domain.operations.UserService
import ru.uniyar.web.validation.UserValidation

class Containers {
    companion object {
        val renderer = PebbleTemplates().HotReload(Config.baseTemplateDirPebble)

        //validate
        val userValidation = UserValidation()

        //service
        val userService = UserService()
    }
}