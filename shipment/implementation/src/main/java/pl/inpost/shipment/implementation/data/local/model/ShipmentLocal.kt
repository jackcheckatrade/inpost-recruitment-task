package pl.inpost.shipment.implementation.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.inpost.shipment.api.model.Customer
import pl.inpost.shipment.api.model.Operations
import pl.inpost.shipment.api.model.Shipment
import pl.inpost.shipment.api.model.ShipmentStatus
import java.time.ZonedDateTime

@Entity(tableName = "shipments")
data class ShipmentLocal(
    @PrimaryKey
    val number: String,
    val shipmentType: String,
    val status: ShipmentStatus,
    val openCode: String?,
    val expiryDate: ZonedDateTime?,
    val storedDate: ZonedDateTime?,
    val pickUpDate: ZonedDateTime?,
    val receiverEmail: String?,
    val senderEmail: String?,
    val manualArchive: Boolean,
    val delete: Boolean,
    val collect: Boolean,
    val highlight: Boolean,
    val expandAvizo: Boolean,
    val endOfWeekCollection: Boolean
) {
    constructor(shipment: Shipment) : this(
        number = shipment.number,
        shipmentType = shipment.shipmentType,
        status = shipment.status,
        openCode = shipment.openCode,
        expiryDate = shipment.expiryDate,
        storedDate = shipment.storedDate,
        pickUpDate = shipment.pickUpDate,
        receiverEmail = shipment.receiver?.email,
        senderEmail = shipment.sender?.email,
        manualArchive = shipment.operations.manualArchive,
        delete = shipment.operations.delete,
        collect = shipment.operations.collect,
        highlight = shipment.operations.highlight,
        expandAvizo = shipment.operations.expandAvizo,
        endOfWeekCollection = shipment.operations.endOfWeekCollection
    )

    fun toDomain(
        receiver: Customer?,
        sender: Customer?
    ) = Shipment(
        number = number,
        shipmentType = shipmentType,
        status = status,
        openCode = openCode,
        expiryDate = expiryDate,
        storedDate = storedDate,
        pickUpDate = pickUpDate,
        receiver = receiver,
        sender = sender,
        operations = Operations(
            manualArchive = manualArchive,
            delete = delete,
            collect = collect,
            highlight = highlight,
            expandAvizo = expandAvizo,
            endOfWeekCollection = endOfWeekCollection
        )
    )
}