package pl.inpost.shipment.api.usecase

import pl.inpost.shipment.api.ShipmentRepository
import pl.inpost.shipment.api.model.Shipment
import java.time.ZonedDateTime
import javax.inject.Inject

class GetGroupedAndSortedShipments @Inject constructor(
    private val shipmentRepository: ShipmentRepository
) {
    suspend operator fun invoke(): Map<Boolean, List<Shipment>> {
        return shipmentRepository.getShipments()
            .sortedWith(
                compareBy(
                    { (it.pickUpDate?.toEpochSecond()?: Long.MAX_VALUE) - ZonedDateTime.now().toEpochSecond() },
                    { (it.expiryDate?.toEpochSecond()?: Long.MAX_VALUE ) - ZonedDateTime.now().toEpochSecond() },
                    { (it.storedDate?.toEpochSecond()?:Long.MAX_VALUE) - ZonedDateTime.now().toEpochSecond() },
                    { it.number }
                )).reversed()
            .groupBy { it.operations.highlight }
    }
}