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
import ru.uniyar.domain.operations.ProjectService
import ru.uniyar.domain.operations.UserService
import ru.uniyar.web.models.ProjectChangedViewModel
import ru.uniyar.web.models.ProjectPageViewModel
import ru.uniyar.web.models.ProjectRegistrationViewModel
import ru.uniyar.web.models.ProjectsPageViewModel
import ru.uniyar.web.validation.ProjectValidation

class GetProjectsHandler(
    val renderer: TemplateRenderer = Containers.renderer,
    val service: ProjectService = Containers.projectService,
) : HttpHandler {
    override fun invoke(request: Request): Response {
        return Response(Status.OK).body(renderer(ProjectsPageViewModel(service.getAllProjects())))
    }
}

class GetProjectByIdHandler(
    val renderer: TemplateRenderer = Containers.renderer,
    val projectService: ProjectService = Containers.projectService,
) : HttpHandler {
    override fun invoke(request: Request): Response {
        val projectId = request.path("id")?.toIntOrNull()
        projectId ?: return Response(Status.BAD_REQUEST)

        val projectSponsors = projectService.getProjectSponsors(projectId) ?: return Response(Status.NOT_FOUND)

        return Response(Status.OK).body(
            renderer(ProjectPageViewModel(projectSponsors))
        )
    }
}


class GetProjectRegistration(
    val renderer: TemplateRenderer = Containers.renderer,
    val userService: UserService = Containers.userService
) : HttpHandler {
    override fun invoke(request: Request): Response {
        return Response(Status.OK).body(renderer(ProjectRegistrationViewModel(users = userService.getAllUsers())))
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

        return redirect(Config.PROJECTS_PATH)
    }

}

class GetProjectUpdate(
    val renderer: TemplateRenderer = Containers.renderer,
    val projectService: ProjectService = Containers.projectService,
    val userService: UserService = Containers.userService
) : HttpHandler {
    override fun invoke(request: Request): Response {
        val projectId = request.path("id")?.toIntOrNull()
        projectId ?: return Response(Status.BAD_REQUEST)
        val project = projectService.getProject(projectId) ?: return Response(Status.NOT_FOUND)
        return Response(Status.OK).body(
            renderer(
                ProjectChangedViewModel(
                    users = userService.getAllUsers(),
                    project = project
                )
            )
        )

    }

}

class PostProjectUpdate(
    val renderer: TemplateRenderer = Containers.renderer,
    val validator: ProjectValidation = Containers.projectValidation,
    val projectService: ProjectService = Containers.projectService,
    val userService: UserService = Containers.userService
) : HttpHandler {
    override fun invoke(request: Request): Response {
        val projectId = request.path("id")?.toIntOrNull()
        projectId ?: return Response(Status.BAD_REQUEST)

        val project = projectService.getProject(projectId) ?: return Response(Status.NOT_FOUND)

        val result = validator.validate(request)
        result.value ?: return Response(Status.BAD_REQUEST).body(
            renderer(
                ProjectChangedViewModel(
                    users = userService.getAllUsers(),
                    project = project,
                    messages = result.errors
                )
            )
        )

        userService.getUser(result.value.userId) ?: return Response(Status.BAD_REQUEST).body(
            renderer(
                ProjectChangedViewModel(
                    users = userService.getAllUsers(),
                    project = project,
                    messages = listOf("User not found")
                )
            )
        )

        projectService.updateProject(projectId, result.value)

        return redirect(Config.PROJECTS_PATH)
    }

}

class PostProjectDelete(
    val projectService: ProjectService = Containers.projectService
) : HttpHandler {
    override fun invoke(request: Request): Response {
        val projectId = request.path("id")?.toIntOrNull()
        projectId ?: return Response(Status.BAD_REQUEST)

        projectService.deleteProject(projectId)

        return redirect(Config.PROJECTS_PATH)
    }

}

