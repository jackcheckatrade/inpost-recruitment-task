package pl.inpost.shipment.api.usecase

import pl.inpost.shipment.api.ShipmentRepository
import javax.inject.Inject

class RefreshShipmentsUseCase @Inject constructor(
    private val shipmentRepository: ShipmentRepository
) {
    suspend operator fun invoke() = runCatching {
        shipmentRepository.fetchShipments()
    }
}