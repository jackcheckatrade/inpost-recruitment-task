package pl.inpost.common.utils

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun ZonedDateTime.formatTime(): String {
    return this.format(DateTimeFormatter.ofPattern("HH:mm"))
}

fun ZonedDateTime.formatDate(): String {
    return this.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
}