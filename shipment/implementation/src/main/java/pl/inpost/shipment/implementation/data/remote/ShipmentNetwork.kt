package pl.inpost.shipment.implementation.data.remote

import com.squareup.moshi.JsonClass
import pl.inpost.shipment.api.model.Shipment
import pl.inpost.shipment.api.model.ShipmentStatus
import java.time.ZonedDateTime

@JsonClass(generateAdapter = true)
data class ShipmentNetwork(
    val number: String,
    val shipmentType: String,
    val status: ShipmentStatus ,
    val eventLog: List<EventLogNetwork>,
    val openCode: String?,
    val expiryDate: ZonedDateTime?,
    val storedDate: ZonedDateTime?,
    val pickUpDate: ZonedDateTime?,
    val receiver: CustomerNetwork?,
    val sender: CustomerNetwork?,
    val operations: OperationsNetwork
) {
    constructor(shipment: Shipment) : this(
        shipment.number,
        shipment.shipmentType,
        shipment.status,
        shipment.eventLog.map { EventLogNetwork(it) },
        shipment.openCode,
        shipment.expiryDate,
        shipment.storedDate,
        shipment.pickUpDate,
        shipment.receiver?.let { CustomerNetwork(it) },
        shipment.sender?.let { CustomerNetwork(it) },
        OperationsNetwork(
            shipment.operations
        )
    )

    fun toDomain(): Shipment = Shipment(
        number = number,
        shipmentType = shipmentType,
        status = status,
        eventLog = eventLog.map { it.toDomain() },
        openCode = openCode,
        expiryDate = expiryDate,
        storedDate = storedDate,
        pickUpDate = pickUpDate,
        receiver = receiver?.toDomain(),
        sender = sender?.toDomain(),
        operations = operations.toDomain()
    )
}
