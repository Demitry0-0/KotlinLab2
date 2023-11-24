package ru.uniyar.web.handlers

import org.http4k.core.Response

fun Response.redirect(path: String) = this.header("Location", path)