package pl.inpost.shipment.api.model

import java.time.ZonedDateTime

data class EventLog(
    val name: String,
    val date: ZonedDateTime
)