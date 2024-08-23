package pl.inpost.shipment.implementation.data.remote.model

import com.squareup.moshi.JsonClass
import pl.inpost.shipment.api.model.Shipment
import pl.inpost.shipment.api.model.ShipmentStatus
import java.time.ZonedDateTime

@JsonClass(generateAdapter = true)
data class ShipmentNetwork(
    val number: String,
    val shipmentType: String,
    val status: ShipmentStatus,
    val openCode: String?,
    val expiryDate: ZonedDateTime?,
    val storedDate: ZonedDateTime?,
    val pickUpDate: ZonedDateTime?,
    val receiver: CustomerNetwork?,
    val sender: CustomerNetwork?,
    val operations: OperationsNetwork
) {
    constructor(shipment: Shipment) : this(
        number = shipment.number,
        shipmentType = "parcel",
        status = shipment.status,
        openCode = shipment.openCode,
        expiryDate = shipment.expiryDate,
        storedDate = shipment.storedDate,
        pickUpDate = shipment.pickUpDate,
        receiver = shipment.receiver?.let { CustomerNetwork(it) },
        sender = shipment.sender?.let { CustomerNetwork(it) },
        operations = OperationsNetwork(shipment.operations)
    )

    fun toDomain(): Shipment = Shipment(
        number = number,
        status = status,
        openCode = openCode,
        expiryDate = expiryDate,
        storedDate = storedDate,
        pickUpDate = pickUpDate,
        receiver = receiver?.toDomain(),
        sender = sender?.toDomain(),
        operations = operations.toDomain()
    )
}
