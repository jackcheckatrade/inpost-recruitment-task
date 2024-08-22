package pl.inpost.shipment.implementation.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pl.inpost.shipment.api.ShipmentRepository
import pl.inpost.shipment.api.model.Shipment
import pl.inpost.shipment.implementation.data.local.ShipmentDao
import pl.inpost.shipment.implementation.data.local.model.ShipmentLocal
import pl.inpost.shipment.implementation.data.remote.api.ShipmentApi
import javax.inject.Inject

class MockedNetworkShipmentRepository @Inject constructor(
    private val shipmentApi: ShipmentApi,
    private val shipmentDao: ShipmentDao
) : ShipmentRepository {

    override suspend fun observeShipments(): Flow<List<Shipment>> {
        return shipmentDao.getShipments().map { shipmentLocalList ->
            shipmentLocalList.map { shipmentLocal ->
                shipmentLocal.toDomain(
                    shipmentLocal.receiverEmail?.let { shipmentDao.getCustomer(it)?.toDomain() },
                    shipmentLocal.senderEmail?.let { shipmentDao.getCustomer(it)?.toDomain() }
                )
            }
        }
    }

    override suspend fun fetchShipments() {
        val shipments = shipmentApi.getShipments().map {
            ShipmentLocal(it.toDomain())
        }
        shipmentDao.insertShipments(*shipments.toTypedArray())
    }

    override suspend fun archiveShipment(shipmentNumber: String) {
        TODO("Not yet implemented")
    }
}