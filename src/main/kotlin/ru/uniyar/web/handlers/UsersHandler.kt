package ru.uniyar.web.handlers

import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.body.formAsMap
import org.http4k.lens.FormField
import org.http4k.lens.Validator
import org.http4k.lens.nonEmptyString
import org.http4k.lens.webForm
import org.http4k.template.TemplateRenderer
import ru.uniyar.Config
import ru.uniyar.Containers
import ru.uniyar.domain.models.User
import ru.uniyar.web.models.UserRegistrationViewModel


class GetUserRegistration(
    val renderer: TemplateRenderer = Containers.renderer
) : HttpHandler {
    override fun invoke(request: Request): Response {
        return Response(Status.OK).body(renderer(UserRegistrationViewModel()))
    }

}

class PostUserRegistration(
    val renderer: TemplateRenderer = Containers.renderer
) : HttpHandler {
    override fun invoke(request: Request): Response {
        val firstNameField = FormField.nonEmptyString().defaulted("firstName", "")
        val lastNameFiled = FormField.nonEmptyString().defaulted("lastName", "")
        val formLens = Body.webForm(Validator.Feedback, firstNameField, lastNameFiled).toLens()
        val form = formLens(request)
        if (form.errors.isEmpty()) {
            val firstName = firstNameField(form)
            val lastName = lastNameFiled(form)
            if (firstName.isName() && lastName.isName()) {
                //save
                return Response(Status.OK).redirect(Config.mainPath)
            }
        }
        val path = request.uri.toString()
        println(path)
        val mp = request.formAsMap().mapValues { it.value.first() }
        println(mp)
        //return Response(Status.FOUND).redirect(path)
        return Response(Status.BAD_REQUEST).body(renderer(UserRegistrationViewModel(mp)))
    }

}

