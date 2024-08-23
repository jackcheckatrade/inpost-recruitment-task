package pl.inpost.shipment.implementation.presentation.shipmentList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.inpost.shipment.api.usecase.ArchiveShipmentUseCase
import pl.inpost.shipment.api.usecase.ObserveGroupedAndSortedShipmentsUseCase
import pl.inpost.shipment.api.usecase.RefreshShipmentsUseCase
import pl.inpost.shipment.implementation.presentation.DateFormatter
import pl.inpost.shipment.implementation.presentation.model.ShipmentDisplayable
import pl.inpost.shipment.implementation.presentation.shipmentList.ShipmentList.ViewState.Companion.DEFAULT_STATE
import javax.inject.Inject

@HiltViewModel
class ShipmentListViewModel @Inject constructor(
    private val observeGroupedAndSortedShipmentsUseCase: ObserveGroupedAndSortedShipmentsUseCase,
    private val refreshShipmentsUseCase: RefreshShipmentsUseCase,
    private val archiveShipmentUseCase: ArchiveShipmentUseCase,
    private val dateFormatter: DateFormatter,
    private val shipmentStatusMapper: ShipmentStatusMapper
) : ViewModel(), ShipmentList.Interaction {

    private val _viewState by lazy { MutableStateFlow(DEFAULT_STATE) }
    val viewState = _viewState.asStateFlow()

    init {
        observeShipments()
        getShipments()
    }

    override fun getShipments() {
        _viewState.update {
            it.copy(
                isLoading = true
            )
        }
        refreshShipments()
    }

    override fun refresh() {
        _viewState.update {
            it.copy(
                isSwipeRefreshing = true
            )
        }
        refreshShipments()
    }

    override fun archiveShipment(shipmentId: String) {
        viewModelScope.launch {
            archiveShipmentUseCase(shipmentId)
        }
    }

    private fun refreshShipments() {
        viewModelScope.launch {
            refreshShipmentsUseCase()
        }
    }

    private fun observeShipments() {
        viewModelScope.launch {
            observeGroupedAndSortedShipmentsUseCase().collect { shipments ->
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
}
