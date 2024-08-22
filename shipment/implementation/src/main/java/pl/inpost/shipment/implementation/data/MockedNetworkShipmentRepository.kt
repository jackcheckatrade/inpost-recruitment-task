package pl.inpost.shipment.implementation.data

import pl.inpost.shipment.api.ShipmentRepository
import pl.inpost.shipment.api.model.Shipment
import pl.inpost.shipment.implementation.data.api.ShipmentApi
import javax.inject.Inject

class MockedNetworkShipmentRepository @Inject constructor(
    private val shipmentApi: ShipmentApi
) : ShipmentRepository {
    override suspend fun getShipments(): List<Shipment> {
        return shipmentApi.getShipments().map {
            it.toDomain()
        }
    }

    override suspend fun archiveShipment(shipmentNumber: String) {
        TODO("Not yet implemented")
    }
}