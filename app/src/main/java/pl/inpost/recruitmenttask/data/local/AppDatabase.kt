package pl.inpost.recruitmenttask.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pl.inpost.shipment.implementation.data.local.ShipmentDao
import pl.inpost.shipment.implementation.data.local.converter.Converters
import pl.inpost.shipment.implementation.data.local.model.CustomerLocal
import pl.inpost.shipment.implementation.data.local.model.ShipmentLocal

@Database(entities = [ShipmentLocal::class, CustomerLocal::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun shipmentDao(): ShipmentDao
}