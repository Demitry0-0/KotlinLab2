package ru.uniyar.web.validation

import org.http4k.lens.Lens
import org.http4k.lens.LensFailure

fun String.isName(): Boolean = this.isNotEmpty() && this.all { it.isWhitespace() || it.isLetter() }

fun <IN : Any, OUT> lensOrNull(
    lens: Lens<IN, OUT?>,
    value: IN,
): OUT? =
    try {
        lens.invoke(value)
    } catch (_: LensFailure) {
        null
    }
