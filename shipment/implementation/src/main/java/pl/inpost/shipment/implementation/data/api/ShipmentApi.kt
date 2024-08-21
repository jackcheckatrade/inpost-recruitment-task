package pl.inpost.shipment.implementation.data.api

import pl.inpost.shipment.implementation.data.remote.ShipmentNetwork

interface ShipmentApi {
    suspend fun getShipments(): List<ShipmentNetwork>
}