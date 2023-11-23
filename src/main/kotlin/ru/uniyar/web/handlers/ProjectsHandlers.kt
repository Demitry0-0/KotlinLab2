package ru.uniyar.web.handlers

import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.body.form
import org.http4k.core.body.formAsMap
import org.http4k.lens.Failure
import org.http4k.lens.FormField
import org.http4k.lens.Validator
import org.http4k.lens.dateTime
import org.http4k.lens.localDate
import org.http4k.lens.long
import org.http4k.lens.nonEmptyString
import org.http4k.lens.string
import org.http4k.lens.webForm
import org.http4k.routing.path
import org.http4k.template.TemplateRenderer
import ru.uniyar.Config
import ru.uniyar.Containers
import ru.uniyar.domain.models.User
import ru.uniyar.domain.storage.Projects
import ru.uniyar.web.models.ProjectPageViewModel
import ru.uniyar.web.models.ProjectRegistrationViewModel
import ru.uniyar.web.models.ProjectsPageViewModel
import java.time.format.DateTimeFormatter
import kotlin.math.max

class ProjectsHandler(
    val renderer: TemplateRenderer,
    val projects: Projects,
) : HttpHandler {
    override fun invoke(request: Request): Response {
        return Response(Status.OK).body(renderer(ProjectsPageViewModel(projects.getAll())))
    }
}

class ProjectByIdHandler(
    val renderer: TemplateRenderer,
    val projects: Projects,
) : HttpHandler {
    override fun invoke(request: Request): Response {
        val projectId = request.path("id")?.toIntOrNull()
        projectId ?: return Response(Status.BAD_REQUEST)

        val model =
            projects.get(projectId)?.let { project ->
                val totalSum = project.sponsors.sumOf { it.second }
                ProjectPageViewModel(
                    project,
                    max(0, project.targetFundSize - totalSum),
                    totalSum,
                )
            } ?: return Response(Status.NOT_FOUND)

        return Response(Status.OK).body(renderer(model))
    }
}


class GetProjectRegistration(
    val renderer: TemplateRenderer = Containers.renderer
) : HttpHandler {
    override fun invoke(request: Request): Response {
        val lst = listOf(
            User(1, "A", "B"),
            User(2, "A", "B"),
        )
        return Response(Status.OK).body(renderer(ProjectRegistrationViewModel(users = lst)))
    }

}

class PostProjectRegistration(
    val renderer: TemplateRenderer = Containers.renderer
) : HttpHandler {
    override fun invoke(request: Request): Response {
        val titleField = FormField.nonEmptyString().required("title")
        val userField = FormField.nonEmptyString().required("user", "")
        val descriptionFiled = FormField.string().optional("description", "")
        val targetFundSizeFiled = FormField.long().required("targetFundSize")
        val startDateFiled = FormField.localDate().required("startDate")
        val endDateFiled = FormField.localDate().required("endDate")

        val formLens = Body.webForm(
            Validator.Feedback, titleField,
            descriptionFiled,
            targetFundSizeFiled,
            startDateFiled,
            endDateFiled
        ).toLens()
        val form = formLens(request)
        if (form.errors.isEmpty()) {
            println(titleField(form))
            println(userField(form))
            println(descriptionFiled(form))
            println(targetFundSizeFiled(form))
            println(startDateFiled(form))
            println(endDateFiled(form))
        }

        return Response(Status.FOUND).redirect(request.uri.toString())
    }

}

