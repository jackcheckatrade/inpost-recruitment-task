package pl.inpost.shipment.implementation.data.remote

import pl.inpost.shipment.api.model.EventLog
import java.time.ZonedDateTime

data class EventLogNetwork(
    val name: String,
    val date: ZonedDateTime
) {
    constructor(eventLog: EventLog) : this(
        eventLog.name,
        eventLog.date
    )

    fun toDomain() = EventLog(
        name = name,
        date = date
    )
}
