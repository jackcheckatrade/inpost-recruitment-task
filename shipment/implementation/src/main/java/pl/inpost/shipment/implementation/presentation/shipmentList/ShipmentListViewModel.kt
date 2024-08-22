package pl.inpost.shipment.implementation.presentation.shipmentList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.inpost.shipment.api.usecase.GetGroupedAndSortedShipments
import pl.inpost.shipment.implementation.data.api.ShipmentApi
import pl.inpost.shipment.implementation.presentation.shipmentList.ShipmentList.ViewState.Companion.DEFAULT_STATE
import javax.inject.Inject

@HiltViewModel
class ShipmentListViewModel @Inject constructor(
    private val getGroupedAndSortedShipments: GetGroupedAndSortedShipments
) : ViewModel(), ShipmentList.Interaction {

    private val _viewState by lazy { MutableStateFlow(DEFAULT_STATE) }
    val viewState = _viewState.asStateFlow()

    init {
        getShipments()
        getShipments()
    }

    override fun getShipments() {
        viewModelScope.launch {
            val shipments = getGroupedAndSortedShipments()
            _viewState.update {
                it.copy(
                    highlightedShipments = shipments[true] ?: emptyList(),
                    shipments = shipments[false] ?: emptyList()
                )
            }
        }
    }

    override fun refresh() {
        TODO("Not yet implemented")
    }

    override fun archiveShipment(shipmentId: String) {
        TODO("Not yet implemented")
    }
}
