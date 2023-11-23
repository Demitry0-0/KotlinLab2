package ru.uniyar.web.handlers

import org.http4k.core.Response

fun Response.redirect(path: String) = this.header("Location", path)
fun String.isName(): Boolean = this.isNotEmpty() && this.all { it.isWhitespace() || it.isLetter() }