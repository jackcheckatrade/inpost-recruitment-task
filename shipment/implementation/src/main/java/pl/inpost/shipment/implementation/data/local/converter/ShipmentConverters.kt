package pl.inpost.shipment.implementation.data.local.converter

import androidx.room.TypeConverter
import pl.inpost.shipment.api.model.ShipmentStatus

class ShipmentConverters {

    @TypeConverter
    fun toStatus(value: Int): ShipmentStatus = ShipmentStatus.entries.first { it.priority == value }

    @TypeConverter
    fun fromStatus(status: ShipmentStatus): Int = status.priority
}