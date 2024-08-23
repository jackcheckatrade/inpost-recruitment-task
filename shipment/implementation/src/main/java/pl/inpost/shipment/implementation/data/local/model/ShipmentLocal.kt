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
    val status: ShipmentStatus,
    val openCode: String?,
    val expiryDate: ZonedDateTime?,
    val storedDate: ZonedDateTime?,
    val pickUpDate: ZonedDateTime?,
    val receiverEmail: String?,
    val receiverName: String?,
    val receiverPhone: String?,
    val senderEmail: String?,
    val senderName: String?,
    val senderPhone: String?,
    val manualArchive: Boolean,
    val delete: Boolean,
    val collect: Boolean,
    val highlight: Boolean,
    val expandAvizo: Boolean,
    val endOfWeekCollection: Boolean
) {
    constructor(shipment: Shipment) : this(
        number = shipment.number,
        status = shipment.status,
        openCode = shipment.openCode,
        expiryDate = shipment.expiryDate,
        storedDate = shipment.storedDate,
        pickUpDate = shipment.pickUpDate,
        receiverEmail = shipment.receiver?.email,
        senderEmail = shipment.sender?.email,
        receiverName = shipment.receiver?.name,
        senderName = shipment.sender?.name,
        receiverPhone = shipment.receiver?.phoneNumber,
        senderPhone = shipment.sender?.phoneNumber,
        manualArchive = shipment.operations.manualArchive,
        delete = shipment.operations.delete,
        collect = shipment.operations.collect,
        highlight = shipment.operations.highlight,
        expandAvizo = shipment.operations.expandAvizo,
        endOfWeekCollection = shipment.operations.endOfWeekCollection
    )

    fun toDomain() = Shipment(
        number = number,
        status = status,
        openCode = openCode,
        expiryDate = expiryDate,
        storedDate = storedDate,
        pickUpDate = pickUpDate,
        receiver = Customer(
            email = receiverEmail,
            name = receiverName,
            phoneNumber = receiverPhone
        ),
        sender = Customer(
            email = senderEmail,
            name = senderName,
            phoneNumber = senderPhone
        ),
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