package pl.inpost.shipment.implementation.presentation.shipmentList

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import pl.inpost.shipment.api.model.ShipmentStatus
import pl.inpost.shipment.implementation.R
import javax.inject.Inject

class ShipmentStatusMapper @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun map(status: ShipmentStatus): String {
        return context.getString(
            when (status) {
                ShipmentStatus.ADOPTED_AT_SORTING_CENTER -> R.string.status_adopted_at_sorting_center
                ShipmentStatus.SENT_FROM_SORTING_CENTER -> R.string.status_sent_from_sorting_center
                ShipmentStatus.ADOPTED_AT_SOURCE_BRANCH -> R.string.status_adopted_at_source_branch
                ShipmentStatus.SENT_FROM_SOURCE_BRANCH -> R.string.status_sent_from_source_branch
                ShipmentStatus.AVIZO -> R.string.status_avizo
                ShipmentStatus.CONFIRMED -> R.string.status_confirmed
                ShipmentStatus.CREATED -> R.string.status_created
                ShipmentStatus.DELIVERED -> R.string.status_delivered
                ShipmentStatus.OTHER -> R.string.status_other
                ShipmentStatus.OUT_FOR_DELIVERY -> R.string.status_out_for_delivery
                ShipmentStatus.PICKUP_TIME_EXPIRED -> R.string.status_pickup_time_expired
                ShipmentStatus.READY_TO_PICKUP -> R.string.status_ready_to_pickup
                ShipmentStatus.RETURNED_TO_SENDER -> R.string.status_returned_to_sender
            }
        )
    }
}