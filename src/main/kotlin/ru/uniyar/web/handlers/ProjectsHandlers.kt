package ru.uniyar.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.path
import org.http4k.template.TemplateRenderer
import ru.uniyar.domain.storage.Projects
import ru.uniyar.web.models.ProjectPageViewModel
import ru.uniyar.web.models.ProjectsPageViewModel
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
