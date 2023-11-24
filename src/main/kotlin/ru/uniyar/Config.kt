package ru.uniyar

class Config {
    companion object {
        const val MAIN_PATH = "/"
        const val PROJECTS_PATH = "/projects"
        const val BASE_TEMPLATE_DIR_PEBBLE = "./src/main/resources"

        const val JDBC_CONNECTION = "jdbc:sqlite:./src/main/resources/db/database.sqlite"
        const val DB_LOCATION = "db/migrations"
        const val DB_DRIVER = "org.sqlite.JDBC"
        const val DB_USER = "sa"
        val DB_PASSWORD = null
    }
}
