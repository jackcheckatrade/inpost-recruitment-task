package pl.inpost.shipment.implementation.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
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
        return shipmentDao.observeShipments().map { shipment ->
            shipment.map { it.toDomain() }
        }
    }

    override suspend fun fetchShipments() =
        withContext(Dispatchers.IO) {
            val shipments = shipmentApi.getShipments().map {
                it.toDomain()
            }
            val localShipment = shipmentDao.getShipments()
            shipmentDao.insertShipments(
                shipments.map { shipment ->
                    val localManualArchive =
                        localShipment.find { it.number == shipment.number }?.manualArchive
                    localManualArchive?.let {
                        shipment.copy(
                            operations = shipment.operations.copy(
                                manualArchive = localManualArchive
                            )
                        )
                    } ?: shipment
                }.map { ShipmentLocal(it) }
            )
        }

    override suspend fun archiveShipment(shipmentNumber: String): Unit =
        withContext(Dispatchers.IO) {
            shipmentDao.getShipment(shipmentNumber)?.let {
                shipmentDao.insertShipments(it.copy(manualArchive = true))
            }
        }

    override suspend fun unarchiveShipment(shipmentNumber: String) {
        withContext(Dispatchers.IO) {
            shipmentDao.getShipment(shipmentNumber)?.let {
                shipmentDao.insertShipments(it.copy(manualArchive = false))
            }
        }
    }
}