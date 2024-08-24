package pl.inpost.shipment.implementation.utils

import pl.inpost.shipment.api.model.Operations
import pl.inpost.shipment.api.model.Shipment
import pl.inpost.shipment.api.model.ShipmentStatus

val shipment = Shipment(
    number = "1",
    status = ShipmentStatus.DELIVERED,
    pickUpDate = null,
    expiryDate = null,
    storedDate = null,
    operations = Operations(
        highlight = false,
        manualArchive = false,
        delete = false,
        collect = false,
        expandAvizo = false,
        endOfWeekCollection = false,
    ),
    openCode = "",
    receiver = null,
    sender = null,
)
val highlightedShipment = Shipment(
    number = "2",
    status = ShipmentStatus.DELIVERED,
    pickUpDate = null,
    expiryDate = null,
    storedDate = null,
    operations = Operations(
        highlight = true,
        manualArchive = false,
        delete = false,
        collect = false,
        expandAvizo = false,
        endOfWeekCollection = false,
    ),
    openCode = "",
    receiver = null,
    sender = null,
)

val archivedShipment = Shipment(
    number = "3",
    status = ShipmentStatus.DELIVERED,
    pickUpDate = null,
    expiryDate = null,
    storedDate = null,
    operations = Operations(
        highlight = true,
        manualArchive = true,
        delete = false,
        collect = false,
        expandAvizo = false,
        endOfWeekCollection = false,
    ),
    openCode = "",
    receiver = null,
    sender = null,
)