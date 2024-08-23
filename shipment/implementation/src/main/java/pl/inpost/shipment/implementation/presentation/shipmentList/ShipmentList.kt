package pl.inpost.shipment.implementation.presentation.shipmentList

import pl.inpost.shipment.implementation.presentation.model.ShipmentDisplayable
import javax.annotation.concurrent.Immutable

interface ShipmentList {
    @Immutable
    data class ViewState(
        val highlightedShipments: List<ShipmentDisplayable>,
        val shipments: List<ShipmentDisplayable>,
        val isLoading: Boolean,
        val isSwipeRefreshing: Boolean,
        val isErrorSnackbarShown: Boolean
    ) {
        companion object {
            val DEFAULT_STATE = ViewState(
                highlightedShipments = emptyList(),
                shipments = emptyList(),
                isLoading = false,
                isSwipeRefreshing = false,
                isErrorSnackbarShown = false
            )
        }

        fun isEmpty() = shipments.isEmpty() && highlightedShipments.isEmpty()
    }

    interface Interaction {
        fun onStart()
        fun getShipments()
        fun refresh()
        fun archiveShipment(shipmentId: String)
        fun onErrorSnackbarShown()
    }
}
