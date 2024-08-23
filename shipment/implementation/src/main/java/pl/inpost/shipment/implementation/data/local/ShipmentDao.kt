package pl.inpost.shipment.implementation.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import pl.inpost.shipment.implementation.data.local.model.ShipmentLocal

@Dao
interface ShipmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShipments(vararg shipments: ShipmentLocal)

    @Transaction
    suspend fun insertShipments(shipments: List<ShipmentLocal>) {
        shipments.forEach { shipment ->
            insertShipments(shipment)
        }
    }

    @Transaction
    @Query("SELECT * FROM shipments")
    fun getShipments(): List<ShipmentLocal>

    @Query("SELECT * FROM shipments")
    fun observeShipments(): Flow<List<ShipmentLocal>>

    @Query("SELECT * FROM shipments WHERE number = :number")
    suspend fun getShipment(number: String): ShipmentLocal?
}