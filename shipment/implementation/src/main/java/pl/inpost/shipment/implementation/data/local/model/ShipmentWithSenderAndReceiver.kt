package pl.inpost.shipment.implementation.data.local.model

import androidx.room.Embedded
import androidx.room.Relation
import pl.inpost.shipment.api.model.Operations
import pl.inpost.shipment.api.model.Shipment

data class ShipmentWithSenderAndReceiver(
    @Embedded
    val shipment: ShipmentLocal,
    @Relation(
        parentColumn = "senderEmail",
        entityColumn = "email"
    )
    val sender: CustomerLocal?,
    @Relation(
        parentColumn = "receiverEmail",
        entityColumn = "email"
    )
    val receiver: CustomerLocal?
){
    constructor(shipment: Shipment): this(
        shipment = ShipmentLocal(shipment),
        sender = shipment.sender?.let { CustomerLocal(it) },
        receiver = shipment.receiver?.let { CustomerLocal(it) }
    )

    fun toDomain() = Shipment(
        number = shipment.number,
        status = shipment.status,
        openCode = shipment.openCode,
        expiryDate = shipment.expiryDate,
        storedDate = shipment.storedDate,
        pickUpDate = shipment.pickUpDate,
        operations = Operations(
            manualArchive = shipment.manualArchive,
            delete = shipment.delete,
            collect = shipment.collect,
            highlight = shipment.highlight,
            expandAvizo = shipment.expandAvizo,
            endOfWeekCollection = shipment.endOfWeekCollection
        ),
        sender = sender?.toDomain(),
        receiver = receiver?.toDomain()
    )
}