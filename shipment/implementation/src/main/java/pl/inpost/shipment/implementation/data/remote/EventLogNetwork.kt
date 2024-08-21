package pl.inpost.shipment.implementation.data.remote

import java.time.ZonedDateTime

data class EventLogNetwork(
    val name: String,
    val date: ZonedDateTime
)
