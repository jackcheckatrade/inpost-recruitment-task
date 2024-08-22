package pl.inpost.shipment.implementation.presentation.shipmentList

import pl.inpost.shipment.api.model.Shipment
import javax.annotation.concurrent.Immutable

interface ShipmentList {
    @Immutable
    data class ViewState(
        val highlightedShipments: List<Shipment>,
        val shipments: List<Shipment>
    ){
        companion object {
            val DEFAULT_STATE = ViewState(
                highlightedShipments = emptyList(),
                shipments = emptyList()
            )
        }
    }

    interface Interaction {
        fun getShipments()
        fun refresh()
        fun archiveShipment(shipmentId: String)
    }
}
