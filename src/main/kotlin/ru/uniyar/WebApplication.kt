package ru.uniyar

import org.flywaydb.core.Flyway
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
import org.ktorm.dsl.insert
import ru.uniyar.domain.models.ProjectModel
import ru.uniyar.domain.models.UserModel
import ru.uniyar.domain.storage.ProjectManager
import ru.uniyar.domain.storage.tables.ProjectTable
import ru.uniyar.domain.storage.tables.SponsorTable
import ru.uniyar.domain.storage.tables.UserTable
import ru.uniyar.web.handlers.GetProjectRegistration
import ru.uniyar.web.handlers.HomeHandler
import ru.uniyar.web.handlers.ProjectByIdHandler
import ru.uniyar.web.handlers.ProjectsHandler
import ru.uniyar.web.handlers.GetUserRegistration
import ru.uniyar.web.handlers.PostProjectRegistration
import ru.uniyar.web.handlers.PostUserRegistration
import java.time.LocalDate
import java.time.LocalDateTime

fun generateProjects(userModels: List<UserModel>): List<ProjectModel> {
    val listProjectModels = mutableListOf<ProjectModel>()
    userModels.first {
        listProjectModels.add(
            ProjectModel(
                0,
                it,
                "На анекдоты",
                "Хочу завести большую базу анекдотов",
                1000000,
//                List((1..userModels.size).random()) {
//                    Pair(userModels.random(), (1000L..1000000L).random())
//                },
                LocalDate.of(2010, 10, 1),
                LocalDate.of(2020, 5, 1),
            ),
        )
    }
    userModels.last {
        listProjectModels.add(
            ProjectModel(
                1,
                it,
                "На кухню",
                "Хочу улучшить условия труда",
                9999999,
//                List((1..userModels.size).random()) {
//                    Pair(userModels.random(), (1000L..1000000L).random())
//                },
                LocalDate.of(2015, 10, 1),
                LocalDate.of(2017, 5, 1),
            ),
        )
    }
    return listProjectModels
}

fun getRoutes(
    renderer: TemplateRenderer
): RoutingHttpHandler =
    routes(
        "/" bind Method.GET to HomeHandler(renderer),
        "/projects" bind Method.GET to ProjectsHandler(renderer),
        "/projects/{id}" bind Method.GET to ProjectByIdHandler(renderer),
        "/registration/user" bind Method.GET to GetUserRegistration(),
        "/registration/user" bind Method.POST to PostUserRegistration(),
        "/registration/project" bind Method.GET to GetProjectRegistration(),
        "/registration/project" bind Method.POST to PostProjectRegistration(),
        static(ResourceLoader.Classpath("/ru/uniyar/public/")),
    )

fun performMigrations() {
    val flyway = Flyway
        .configure()
        .locations(Config.DB_LOCATION)
        .validateMigrationNaming(true)
        .dataSource(Config.JDBC_CONNECTION, Config.DB_USER, Config.DB_PASSWORD)
        .load()
    flyway.migrate()
}

val generatedUserModels =
    listOf(
        UserModel(0, "Alex", "Bond"),
        UserModel(1, "Homa", "Round"),
        UserModel(2, "Nastya", "Rukastai"),
    )


fun main() {
    performMigrations()
    val renderer = PebbleTemplates().HotReload("./src/main/resources")
    val app: HttpHandler = getRoutes(renderer)

    val server = app.asServer(Netty(port = 9000))
    server.start()

    println("Server started on " + server.port())
}
