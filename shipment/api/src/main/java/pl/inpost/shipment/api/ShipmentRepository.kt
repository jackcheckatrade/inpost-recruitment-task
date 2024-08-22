package pl.inpost.shipment.api

import pl.inpost.shipment.api.model.Shipment

interface ShipmentRepository {
    suspend fun getShipments(): List<Shipment>
    suspend fun archiveShipment(shipmentNumber: String)
}