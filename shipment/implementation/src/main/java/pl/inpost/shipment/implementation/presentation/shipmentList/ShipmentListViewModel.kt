package pl.inpost.shipment.implementation.presentation.shipmentList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
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

    private var shipmentsJob: Job? = null

    override fun onStart() {
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
                .onFailure {
                    showErrorSnackbar()
                }
        }
    }

    override fun onErrorSnackbarShown() {
        _viewState.update {
            it.copy(
                isErrorSnackbarShown = false
            )
        }
    }

    private fun refreshShipments() {
        viewModelScope.launch {
            refreshShipmentsUseCase()
                .onSuccess {
                    hideLoadingIndicators()
                }
                .onFailure {
                    hideLoadingIndicators()
                    showErrorSnackbar()
                }
        }
    }

    private fun observeShipments() {
        shipmentsJob?.cancel()
        shipmentsJob = viewModelScope.launch {
            observeGroupedAndSortedShipmentsUseCase()
                .catch { showErrorSnackbar() }
                .collect { shipments ->
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

    private fun hideLoadingIndicators() {
        _viewState.update {
            it.copy(
                isLoading = false,
                isSwipeRefreshing = false
            )
        }
    }

    private fun showErrorSnackbar() {
        _viewState.update {
            it.copy(
                isErrorSnackbarShown = true
            )
        }
    }
}
