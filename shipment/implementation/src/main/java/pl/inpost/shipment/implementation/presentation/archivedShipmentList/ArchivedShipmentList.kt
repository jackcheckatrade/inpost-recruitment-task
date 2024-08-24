package pl.inpost.shipment.implementation.presentation.archivedShipmentList

import pl.inpost.shipment.implementation.presentation.model.ShipmentDisplayable
import javax.annotation.concurrent.Immutable

interface ArchivedShipmentList {
    @Immutable
    data class ViewState(
        val shipments: List<ShipmentDisplayable>,
        val isLoading: Boolean,
    ) {
        companion object {
            val DEFAULT_STATE = ViewState(
                shipments = emptyList(),
                isLoading = false,
            )
        }
    }

    interface Interaction {
        fun onStart()
        fun onMoreClicked(shipmentId: String)
    }
}