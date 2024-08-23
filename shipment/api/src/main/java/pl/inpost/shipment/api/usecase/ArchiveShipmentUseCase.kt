package pl.inpost.shipment.api.usecase

import pl.inpost.shipment.api.ShipmentRepository
import javax.inject.Inject

class ArchiveShipmentUseCase @Inject constructor(
    private val shipmentRepository: ShipmentRepository
) {
    suspend operator fun invoke(shipmentId: String) {
        shipmentRepository.archiveShipment(shipmentId)
    }
}