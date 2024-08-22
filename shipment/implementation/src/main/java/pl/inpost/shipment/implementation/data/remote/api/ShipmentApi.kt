package pl.inpost.shipment.implementation.data.remote.api

import pl.inpost.shipment.implementation.data.remote.model.ShipmentNetwork

interface ShipmentApi {
    suspend fun getShipments(): List<ShipmentNetwork>
}