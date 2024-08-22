package pl.inpost.shipment.implementation.presentation.shipmentList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.inpost.shipment.api.usecase.GetGroupedAndSortedShipments
import pl.inpost.shipment.implementation.presentation.DateFormatter
import pl.inpost.shipment.implementation.presentation.model.ShipmentDisplayable
import pl.inpost.shipment.implementation.presentation.shipmentList.ShipmentList.ViewState.Companion.DEFAULT_STATE
import javax.inject.Inject

@HiltViewModel
class ShipmentListViewModel @Inject constructor(
    private val getGroupedAndSortedShipments: GetGroupedAndSortedShipments,
    private val dateFormatter: DateFormatter,
    private val shipmentStatusMapper: ShipmentStatusMapper
) : ViewModel(), ShipmentList.Interaction {

    private val _viewState by lazy { MutableStateFlow(DEFAULT_STATE) }
    val viewState = _viewState.asStateFlow()

    init {
        getShipments()
    }

    override fun getShipments() {
        _viewState.update {
            it.copy(
                isLoading = true
            )
        }
        fetchShipments()
    }

    override fun refresh() {
        _viewState.update {
            it.copy(
                isSwipeRefreshing = true
            )
        }
        fetchShipments()
    }

    override fun archiveShipment(shipmentId: String) {
        TODO("Not yet implemented")
    }

    private fun fetchShipments() {
        viewModelScope.launch {
            val shipments = getGroupedAndSortedShipments()
            _viewState.update { state ->
                state.copy(
                    highlightedShipments = shipments[true]?.map {
                        ShipmentDisplayable.fromDomain(
                            it,
                            shipmentStatusMapper,
                            dateFormatter
                        )
                    } ?: emptyList(),
                    shipments = shipments[false]?.map {
                        ShipmentDisplayable.fromDomain(
                            it,
                            shipmentStatusMapper,
                            dateFormatter
                        )
                    } ?: emptyList(),
                    isLoading = false,
                    isSwipeRefreshing = false
                )
            }
        }
    }
}
