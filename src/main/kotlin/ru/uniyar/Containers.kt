package ru.uniyar

import org.http4k.template.PebbleTemplates
import org.ktorm.database.Database
import org.ktorm.support.sqlite.SQLiteDialect
import ru.uniyar.Config.Companion.DB_DRIVER
import ru.uniyar.Config.Companion.DB_USER
import ru.uniyar.Config.Companion.JDBC_CONNECTION
import ru.uniyar.domain.operations.ProjectService
import ru.uniyar.domain.operations.UserService
import ru.uniyar.web.validation.ProjectValidation
import ru.uniyar.web.validation.UserValidation

class Containers {
    companion object {
        val renderer = PebbleTemplates().HotReload(Config.BASE_TEMPLATE_DIR_PEBBLE)

        //validate
        val userValidation = UserValidation()
        val projectValidation = ProjectValidation()

        //service
        val userService = UserService()
        val projectService = ProjectService()

        // database
        val dialect = SQLiteDialect()
        val database = Database.connect(
            url = JDBC_CONNECTION,
            driver = DB_DRIVER,
            user = DB_USER,
            dialect = dialect,
        )
    }
}