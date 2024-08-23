package pl.inpost.shipment.implementation.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import pl.inpost.shipment.api.model.Shipment
import pl.inpost.shipment.implementation.data.local.model.CustomerLocal
import pl.inpost.shipment.implementation.data.local.model.ShipmentLocal
import pl.inpost.shipment.implementation.data.local.model.ShipmentWithSenderAndReceiver

@Dao
interface ShipmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShipments(vararg shipments: ShipmentLocal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomer(customer: CustomerLocal)

    @Transaction
    suspend fun insertShipments(shipments: List<ShipmentWithSenderAndReceiver>) {
        shipments.forEach { shipment ->
            insertShipments(shipment.shipment)
            shipment.sender?.let { insertCustomer(it) }
            shipment.receiver?.let { insertCustomer(it) }
        }
    }

    @Transaction
    @Query("SELECT * FROM shipments")
    fun getShipments(): List<ShipmentWithSenderAndReceiver>

    @Query("SELECT * FROM shipments")
    fun observeShipments(): Flow<List<ShipmentWithSenderAndReceiver>>

    @Query("SELECT * FROM customers WHERE email = :email LIMIT 1")
    suspend fun getCustomer(email: String): CustomerLocal?

    @Query("SELECT * FROM shipments WHERE number = :number")
    suspend fun getShipment(number: String): ShipmentLocal?
}