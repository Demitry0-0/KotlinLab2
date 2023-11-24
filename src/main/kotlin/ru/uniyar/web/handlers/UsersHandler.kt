package ru.uniyar.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.body.formAsMap
import org.http4k.template.TemplateRenderer
import ru.uniyar.Config
import ru.uniyar.Containers
import ru.uniyar.domain.operations.UserService
import ru.uniyar.web.models.UserRegistrationViewModel
import ru.uniyar.web.validation.UserValidation


class GetUserRegistration(
    val renderer: TemplateRenderer = Containers.renderer
) : HttpHandler {
    override fun invoke(request: Request): Response {
        return Response(Status.OK).body(renderer(UserRegistrationViewModel()))
    }

}

class PostUserRegistration(
    val renderer: TemplateRenderer = Containers.renderer,
    val validator: UserValidation = Containers.userValidation,
    val service: UserService = Containers.userService
) : HttpHandler {
    override fun invoke(request: Request): Response {
        val result = validator.validate(request)
        result.value ?: return Response(Status.BAD_REQUEST).body(
            renderer(
                UserRegistrationViewModel(
                    request.formAsMap().mapValues { it.value.first() },
                    messages = result.errors
                )
            )
        )
        service.createUser(result.value)

        return Response(Status.FOUND).redirect(Config.MAIN_PATH)
    }

}

