package ru.uniyar.web.validation

import org.http4k.core.Body
import org.http4k.core.Request
import org.http4k.lens.FormField
import org.http4k.lens.Validator
import org.http4k.lens.nonEmptyString
import org.http4k.lens.webForm
import ru.uniyar.dto.User

class UserValidation {
    val firstNameField = FormField.nonEmptyString().defaulted("firstName", "")
    val lastNameFiled = FormField.nonEmptyString().defaulted("lastName", "")
    fun validate(request: Request): ResultValidate<User> {
        val formLens = Body.webForm(Validator.Feedback, firstNameField, lastNameFiled).toLens()
        val form = formLens(request)
        if (form.errors.isNotEmpty())
            return ResultValidate(null, form.errors.map { it.toString() })
        val firstName = firstNameField(form)
        val lastName = lastNameFiled(form)
        if (!(firstName.isName() || lastName.isName()))
            return ResultValidate(null, listOf("name must not be empty"))
        return ResultValidate(
            User(firstName, lastName),
            listOf()
        )
    }
}