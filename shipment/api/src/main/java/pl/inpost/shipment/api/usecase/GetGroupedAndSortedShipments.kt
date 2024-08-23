package pl.inpost.shipment.api.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pl.inpost.shipment.api.ShipmentRepository
import pl.inpost.shipment.api.model.Shipment
import java.time.ZonedDateTime
import javax.inject.Inject
import kotlin.math.abs

class ObserveGroupedAndSortedShipmentsUseCase @Inject constructor(
    private val shipmentRepository: ShipmentRepository
) {
    suspend operator fun invoke(): Flow<Map<Boolean, List<Shipment>>> {
        return shipmentRepository.observeShipments()
            .map {
                it.filter { shipment: Shipment -> !shipment.operations.manualArchive }.sortedWith(
                    compareByDescending<Shipment>
                    { it.status.priority }
                        .thenBy {
                            // the closer to today, the greater the priority
                            abs(
                                (it.pickUpDate?.toEpochSecond()
                                    ?: Long.MAX_VALUE) - ZonedDateTime.now().toEpochSecond()
                            )
                        }
                        .thenBy {
                            // the closer to today, the greater the priority
                            abs(
                                (it.expiryDate?.toEpochSecond()
                                    ?: Long.MAX_VALUE) - ZonedDateTime.now().toEpochSecond()
                            )
                        }
                        .thenBy {
                            // the closer to today, the greater the priority
                            abs(
                                (it.storedDate?.toEpochSecond()
                                    ?: Long.MAX_VALUE) - ZonedDateTime.now().toEpochSecond()
                            )
                        }
                        .thenBy { it.number }
                )
                    .groupBy { it.operations.highlight }
            }
    }
}