package pl.inpost.shipment.implementation.presentation.model

import pl.inpost.shipment.api.model.Shipment
import pl.inpost.shipment.api.model.ShipmentStatus
import pl.inpost.shipment.implementation.presentation.DateFormatter
import pl.inpost.shipment.implementation.presentation.shipmentList.ShipmentStatusMapper

data class ShipmentDisplayable(
    val number: String,
    val status: ShipmentStatus,
    val statusString: String,
    val expiryDate: DateTimeDisplayable?,
    val storedDate: DateTimeDisplayable?,
    val pickUpDate: DateTimeDisplayable?,
    val sender: String,
) {
    companion object {
        fun fromDomain(
            shipment: Shipment,
            shipmentStatusMapper: ShipmentStatusMapper,
            dateFormatter: DateFormatter,
        ): ShipmentDisplayable {
            return ShipmentDisplayable(
                number = shipment.number,
                status = shipment.status,
                statusString = shipmentStatusMapper.map(shipment.status),
                expiryDate = shipment.expiryDate?.let { dateFormatter.format(it) },
                storedDate = shipment.storedDate?.let { dateFormatter.format(it) },
                pickUpDate = shipment.pickUpDate?.let { dateFormatter.format(it) },
                sender = shipment.sender?.name ?: ""
            )
        }
    }

    fun isDateVisible(): Boolean {
        return status == ShipmentStatus.READY_TO_PICKUP || status == ShipmentStatus.DELIVERED
    }
}

data class DateTimeDisplayable(
    val dayOfWeekShort: String,
    val date: String,
    val time: String
) {
}
