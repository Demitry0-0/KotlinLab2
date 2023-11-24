package ru.uniyar

class Config {
    companion object {
        const val MAIN_PATH = "/"
        const val BASE_TEMPLATE_DIR_PEBBLE = "./src/main/resources"
        const val JDBC_CONNECTION = "jdbc:sqlite:./src/main/resources/db/data.sqlite"
        const val DB_DRIVER = "org.sqlite.JDBC"
        const val DB_USER = "sa"

    }
}