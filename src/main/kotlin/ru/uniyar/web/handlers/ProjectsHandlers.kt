package ru.uniyar.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.body.formAsMap
import org.http4k.routing.path
import org.http4k.template.TemplateRenderer
import ru.uniyar.Config
import ru.uniyar.Containers
import ru.uniyar.domain.models.UserModel
import ru.uniyar.domain.operations.ProjectService
import ru.uniyar.domain.operations.UserService
import ru.uniyar.web.models.ProjectRegistrationViewModel
import ru.uniyar.web.models.ProjectsPageViewModel
import ru.uniyar.web.validation.ProjectValidation

class ProjectsHandler(
    val renderer: TemplateRenderer = Containers.renderer,
    val service: ProjectService = Containers.projectService,
) : HttpHandler {
    override fun invoke(request: Request): Response {
        return Response(Status.OK).body(renderer(ProjectsPageViewModel(service.getAllProjects())))
    }
}

class ProjectByIdHandler(
    val renderer: TemplateRenderer = Containers.renderer,
    val service: ProjectService = Containers.projectService,
) : HttpHandler {
    override fun invoke(request: Request): Response {
        val projectId = request.path("id")?.toIntOrNull()
        projectId ?: return Response(Status.BAD_REQUEST)
        return Response(Status.NOT_FOUND)
//        val model =
//            projects.get(projectId)?.let { project ->
//                val totalSum = project.sponsors.sumOf { it.second }
//                ProjectPageViewModel(
//                    project,
//                    max(0, project.targetFundSize - totalSum),
//                    totalSum,
//                )
//            } ?: return Response(Status.NOT_FOUND)
//
//        return Response(Status.OK).body(renderer(model))
    }
}


val lst = listOf(
    UserModel(1, "A", "B"),
    UserModel(2, "A", "B"),
)

class GetProjectRegistration(
    val renderer: TemplateRenderer = Containers.renderer
) : HttpHandler {
    override fun invoke(request: Request): Response {
        return Response(Status.OK).body(renderer(ProjectRegistrationViewModel(users = lst)))
    }

}

class PostProjectRegistration(
    val renderer: TemplateRenderer = Containers.renderer,
    val validator: ProjectValidation = Containers.projectValidation,
    val projectService: ProjectService = Containers.projectService,
    val userService: UserService = Containers.userService
) : HttpHandler {
    override fun invoke(request: Request): Response {
        val result = validator.validate(request)
        result.value ?: return Response(Status.BAD_REQUEST).body(
            renderer(
                ProjectRegistrationViewModel(
                    users = userService.getAllUsers(),
                    project = request.formAsMap().mapValues { it.value.first() },
                    messages = result.errors
                )
            )
        )

        userService.getUser(result.value.userId) ?: return Response(Status.BAD_REQUEST).body(
            renderer(
                ProjectRegistrationViewModel(
                    users = userService.getAllUsers(),
                    project = request.formAsMap().mapValues { it.value.first() },
                    messages = listOf("User not found")
                )
            )
        )

        projectService.createProject(result.value)

        return Response(Status.FOUND).redirect(Config.MAIN_PATH)
    }

}

