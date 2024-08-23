package pl.inpost.recruitmenttask.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pl.inpost.android_common.data.local.converter.DatabaseCommonConverters
import pl.inpost.shipment.implementation.data.local.ShipmentDao
import pl.inpost.shipment.implementation.data.local.converter.ShipmentConverters
import pl.inpost.shipment.implementation.data.local.model.ShipmentLocal

@Database(entities = [ShipmentLocal::class], version = 1)
@TypeConverters(DatabaseCommonConverters::class, ShipmentConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun shipmentDao(): ShipmentDao
}