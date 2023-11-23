package ru.uniyar

import org.http4k.template.PebbleTemplates

class Containers {
    companion object {
        val renderer = PebbleTemplates().HotReload("./src/main/resources")

    }
}