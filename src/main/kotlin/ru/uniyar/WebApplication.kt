package ru.uniyar

import org.flywaydb.core.Flyway
import org.http4k.core.Method
import org.http4k.routing.ResourceLoader
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.routing.static
import org.http4k.server.Netty
import org.http4k.server.asServer
import ru.uniyar.web.handlers.GetProjectRegistration
import ru.uniyar.web.handlers.GetUserRegistration
import ru.uniyar.web.handlers.HomeHandler
import ru.uniyar.web.handlers.PostProjectRegistration
import ru.uniyar.web.handlers.PostUserRegistration
import ru.uniyar.web.handlers.GetProjectByIdHandler
import ru.uniyar.web.handlers.GetProjectsHandler
import ru.uniyar.web.handlers.GetUsersHandler

val app = routes(
    "/" bind Method.GET to HomeHandler(),
    "/users" bind Method.GET to GetUsersHandler(),
    "/projects" bind Method.GET to GetProjectsHandler(),
    "/projects/{id}" bind Method.GET to GetProjectByIdHandler(),
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


fun main() {
    performMigrations()

    val server = app.asServer(Netty(port = 9000))
    server.start()

    println("Server started on " + server.port())
}
