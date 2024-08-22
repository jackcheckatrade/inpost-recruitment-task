package pl.inpost.shipment.api.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pl.inpost.shipment.api.ShipmentRepository
import pl.inpost.shipment.api.model.Shipment
import java.time.ZonedDateTime
import javax.inject.Inject
import kotlin.math.abs

class ObserveGroupedAndSortedShipments @Inject constructor(
    private val shipmentRepository: ShipmentRepository
) {
    suspend operator fun invoke(): Flow<Map<Boolean, List<Shipment>>> {
        return shipmentRepository.observeShipments()
            .map {
                it.sortedWith(
                    compareBy(
                        { it.status.priority },
                        {
                            abs(
                                (it.pickUpDate?.toEpochSecond()
                                    ?: Long.MAX_VALUE) - ZonedDateTime.now().toEpochSecond()
                            )
                        },
                        {
                            abs(
                                (it.expiryDate?.toEpochSecond()
                                    ?: Long.MAX_VALUE) - ZonedDateTime.now().toEpochSecond()
                            )
                        },
                        {
                            abs(
                                (it.storedDate?.toEpochSecond()
                                    ?: Long.MAX_VALUE) - ZonedDateTime.now().toEpochSecond()
                            )
                        },
                        { it.number }
                    )).reversed()
                    .groupBy { it.operations.highlight }
            }
    }
}