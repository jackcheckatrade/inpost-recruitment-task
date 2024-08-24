package pl.inpost.shipment.implementation.presentation.archivedShipmentList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.inpost.shipment.api.usecase.ArchiveShipmentUseCase
import pl.inpost.shipment.api.usecase.ObserveGroupedAndSortedShipmentsUseCase
import pl.inpost.shipment.implementation.presentation.DateFormatter
import pl.inpost.shipment.implementation.presentation.model.ShipmentDisplayable
import pl.inpost.shipment.implementation.presentation.shipmentList.ShipmentStatusMapper
import javax.inject.Inject

@HiltViewModel
class ArchivedShipmentListViewModel @Inject constructor(
    private val observeGroupedAndSortedShipmentsUseCase: ObserveGroupedAndSortedShipmentsUseCase,
    private val archiveShipmentUseCase: ArchiveShipmentUseCase,
    private val dateFormatter: DateFormatter,
    private val shipmentStatusMapper: ShipmentStatusMapper,
) : ViewModel(), ArchivedShipmentList.Interaction {

    private val _viewState by lazy { MutableStateFlow(ArchivedShipmentList.ViewState.DEFAULT_STATE) }
    val viewState = _viewState.asStateFlow()

    override fun onStart() {
        viewModelScope.launch {
            observeGroupedAndSortedShipmentsUseCase.invoke(showArchived = true)
                .collect { shipments ->
                    _viewState.update {
                        it.copy(
                            shipments = shipments.values.flatten()
                                .map { shipment ->
                                    ShipmentDisplayable.fromDomain(
                                        shipment,
                                        dateFormatter = dateFormatter,
                                        shipmentStatusMapper = shipmentStatusMapper
                                    )
                                }
                        )
                    }
                }
        }
    }

    override fun onMoreClicked(shipmentId: String) {
        viewModelScope.launch {
            archiveShipmentUseCase(shipmentId, true)
        }
    }
}