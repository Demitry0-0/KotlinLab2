package ru.uniyar

import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.routing.ResourceLoader
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.routing.static
import org.http4k.server.Netty
import org.http4k.server.asServer
import org.http4k.template.PebbleTemplates
import org.http4k.template.TemplateRenderer
import ru.uniyar.domain.models.Project
import ru.uniyar.domain.models.User
import ru.uniyar.domain.storage.Projects
import ru.uniyar.domain.storage.Users
import ru.uniyar.web.handlers.HomeHandler
import ru.uniyar.web.handlers.ProjectByIdHandler
import ru.uniyar.web.handlers.ProjectsHandler
import java.time.LocalDateTime

fun generateProjects(users: List<User>): List<Project> {
    val listProjects = mutableListOf<Project>()
    users.first {
        listProjects.add(
            Project(
                0,
                it,
                "На анекдоты",
                "Хочу завести большую базу анекдотов",
                1000000,
                List((0..users.size).random()) {
                    Pair(users.random(), (1000L..1000000L).random())
                },
                LocalDateTime.of(2010, 10, 1, 0, 0, 0),
                LocalDateTime.of(2020, 5, 1, 0, 0, 0),
                LocalDateTime.of(2010, 10, 1, 0, 0, 0),
            ),
        )
    }
    users.last {
        listProjects.add(
            Project(
                1,
                it,
                "На кухню",
                "Хочу улучшить условия труда",
                9999999,
                List((0..users.size).random()) {
                    Pair(users.random(), (1000L..1000000L).random())
                },
                LocalDateTime.of(2015, 10, 1, 0, 0, 0),
                LocalDateTime.of(2017, 5, 1, 0, 0, 0),
                LocalDateTime.of(2015, 10, 1, 0, 0, 0),
            ),
        )
    }
    return listProjects
}

fun getRoutes(
    renderer: TemplateRenderer,
    projects: Projects,
): RoutingHttpHandler =
    routes(
        "/" bind Method.GET to HomeHandler(renderer),
        "/projects" bind Method.GET to ProjectsHandler(renderer, projects),
        "/projects/{id}" bind Method.GET to ProjectByIdHandler(renderer, projects),
        static(ResourceLoader.Classpath("/ru/uniyar/public/")),
    )

val users = Users()
val projects = Projects()

val generatedUsers =
    listOf(
        User(0, "Alex", "Bond"),
        User(1, "Homa", "Round"),
        User(2, "Nastya", "Rukastai"),
    )

fun main() {
    users.fill(generatedUsers)
    projects.fill(generateProjects(generatedUsers))

    val renderer = PebbleTemplates().HotReload("src/main/resources")
    val app: HttpHandler = getRoutes(renderer, projects)

    val server = app.asServer(Netty(port = 9000))
    server.start()

    println("Server started on " + server.port())
}