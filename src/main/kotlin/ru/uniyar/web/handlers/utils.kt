package ru.uniyar.web.handlers

import org.http4k.core.Response
import org.http4k.core.Status

fun redirect(path: String): Response = Response(Status.FOUND).header("Location", path)
