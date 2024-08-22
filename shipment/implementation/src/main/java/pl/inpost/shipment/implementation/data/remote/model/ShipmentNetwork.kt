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
    fun toDomain(): Shipment = Shipment(
        number = number,
        shipmentType = shipmentType,
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
