package pl.inpost.shipment.implementation.data.local.converter

import androidx.room.TypeConverter
import pl.inpost.shipment.api.model.ShipmentStatus
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class Converters {
    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @TypeConverter
    fun toZonedDateTime(value: String): ZonedDateTime = formatter.parse(value, ZonedDateTime::from)

    @TypeConverter
    fun fromZonedDateTime(date: ZonedDateTime?): String? = date?.format(formatter)

    @TypeConverter
    fun toStatus(value: Int): ShipmentStatus = ShipmentStatus.entries.first { it.priority == value }

    @TypeConverter
    fun fromStatus(status: ShipmentStatus): Int = status.priority
}