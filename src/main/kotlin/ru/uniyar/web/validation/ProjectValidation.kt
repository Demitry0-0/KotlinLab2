package ru.uniyar.web.validation

import org.http4k.core.Body
import org.http4k.core.Request
import org.http4k.lens.FormField
import org.http4k.lens.Validator
import org.http4k.lens.localDate
import org.http4k.lens.long
import org.http4k.lens.nonEmptyString
import org.http4k.lens.string
import org.http4k.lens.webForm
import ru.uniyar.dto.Project

class ProjectValidation {
    val titleField = FormField.nonEmptyString().required("title")
    val userField = FormField.nonEmptyString().required("user", "")
    val descriptionFiled = FormField.string().defaulted("description", "")
    val targetFundSizeFiled = FormField.long().required("targetFundSize")
    val startDateFiled = FormField.localDate().required("startDate")
    val endDateFiled = FormField.localDate().required("endDate")


    fun validate(request: Request): ResultValidate<Project> {
        val formLens = Body.webForm(
            Validator.Feedback, titleField,
            descriptionFiled,
            targetFundSizeFiled,
            startDateFiled,
            endDateFiled
        ).toLens()
        val form = formLens(request)
        if (form.errors.isNotEmpty())
            return ResultValidate(null, form.errors.map { it.toString() })

        val user = userField(form)
        val userId = user.split("\\s+".toRegex()).first().toIntOrNull()
        userId ?: return ResultValidate(null, listOf("user id not found"))

        val targetFundSize = targetFundSizeFiled(form)
        if (targetFundSize < 0) return ResultValidate(null, listOf("target fund size is negative"))

        val startDate = startDateFiled(form)
        val endDate = endDateFiled(form)
        if (startDate >= endDate) return ResultValidate(null, listOf("bad date. start >= end"))

        val title = titleField(form)
        val description = descriptionFiled(form)

        return ResultValidate(Project(title, userId, description, targetFundSize, startDate, endDate), listOf())
    }
}