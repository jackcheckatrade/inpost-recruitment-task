package pl.inpost.shipment.implementation.data.api

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import pl.inpost.shipment.api.model.ShipmentStatus

class ShipmentStatusJsonAdapter {
    @FromJson
    fun fromJson(json: String): ShipmentStatus = when (json) {
        "CREATED" -> ShipmentStatus.CREATED
        "CONFIRMED" -> ShipmentStatus.CONFIRMED
        "ADOPTED_AT_SOURCE_BRANCH" -> ShipmentStatus.ADOPTED_AT_SOURCE_BRANCH
        "SENT_FROM_SOURCE_BRANCH" -> ShipmentStatus.SENT_FROM_SOURCE_BRANCH
        "ADOPTED_AT_SORTING_CENTER" -> ShipmentStatus.ADOPTED_AT_SORTING_CENTER
        "SENT_FROM_SORTING_CENTER" -> ShipmentStatus.SENT_FROM_SORTING_CENTER
        "OTHER" -> ShipmentStatus.OTHER
        "DELIVERED" -> ShipmentStatus.DELIVERED
        "RETURNED_TO_SENDER" -> ShipmentStatus.RETURNED_TO_SENDER
        "AVIZO" -> ShipmentStatus.AVIZO
        "OUT_FOR_DELIVERY" -> ShipmentStatus.OUT_FOR_DELIVERY
        "READY_TO_PICKUP" -> ShipmentStatus.READY_TO_PICKUP
        "PICKUP_TIME_EXPIRED" -> ShipmentStatus.PICKUP_TIME_EXPIRED
        else -> ShipmentStatus.OTHER
    }


    @ToJson
    fun toJson(status: ShipmentStatus): String = when (status) {
        ShipmentStatus.CREATED -> "CREATED"
        ShipmentStatus.CONFIRMED -> "CONFIRMED"
        ShipmentStatus.ADOPTED_AT_SOURCE_BRANCH -> "ADOPTED_AT_SOURCE_BRANCH"
        ShipmentStatus.SENT_FROM_SOURCE_BRANCH -> "SENT_FROM_SOURCE_BRANCH"
        ShipmentStatus.ADOPTED_AT_SORTING_CENTER -> "ADOPTED_AT_SORTING_CENTER"
        ShipmentStatus.SENT_FROM_SORTING_CENTER -> "SENT_FROM_SORTING_CENTER"
        ShipmentStatus.OTHER -> "OTHER"
        ShipmentStatus.DELIVERED -> "DELIVERED"
        ShipmentStatus.RETURNED_TO_SENDER -> "RETURNED_TO_SENDER"
        ShipmentStatus.AVIZO -> "AVIZO"
        ShipmentStatus.OUT_FOR_DELIVERY -> "OUT_FOR_DELIVERY"
        ShipmentStatus.READY_TO_PICKUP -> "READY_TO_PICKUP"
        ShipmentStatus.PICKUP_TIME_EXPIRED -> "PICKUP_TIME_EXPIRED"
    }
}
