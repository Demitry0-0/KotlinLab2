package ru.uniyar.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.template.TemplateRenderer
import ru.uniyar.web.models.HomePageViewModel

class HomeHandler(
    val renderer: TemplateRenderer,
) : HttpHandler {
    override fun invoke(request: Request): Response {
        return Response(Status.OK).body(renderer(HomePageViewModel()))
    }
}
