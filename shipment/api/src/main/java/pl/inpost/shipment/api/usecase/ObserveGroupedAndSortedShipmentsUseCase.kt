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
                it.filter { shipment: Shipment ->!shipment.operations.manualArchive }.sortedWith(
                    compareByDescending<Shipment>
                    { it.status.priority }
                        .thenBy { calculateDatePriority(it.pickUpDate) }
                        .thenBy { calculateDatePriority(it.expiryDate) }
                        .thenBy { calculateDatePriority(it.storedDate) }
                        .thenBy { it.number }
                ).groupBy { it.operations.highlight }
            }
    }

    // the closest date to current date should be at top of the list
    private fun calculateDatePriority(date: ZonedDateTime?): Long {
        val currentDateTime = ZonedDateTime.now()

        val dateEpochSecond =
            date?.let {
                // if date is before current date, subtract 100 years to it to send it to the bottom of the list
                if (date.isBefore(currentDateTime))
                    date.minusYears(100).toEpochSecond()
                else
                    date.toEpochSecond()
            } ?: Long.MAX_VALUE

        return abs(dateEpochSecond - currentDateTime.toEpochSecond())
    }
}