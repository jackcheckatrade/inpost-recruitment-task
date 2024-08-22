package pl.inpost.shipment.api

import kotlinx.coroutines.flow.Flow
import pl.inpost.shipment.api.model.Shipment

interface ShipmentRepository {
    suspend fun observeShipments(): Flow<List<Shipment>>
    suspend fun fetchShipments()
    suspend fun archiveShipment(shipmentNumber: String)
}