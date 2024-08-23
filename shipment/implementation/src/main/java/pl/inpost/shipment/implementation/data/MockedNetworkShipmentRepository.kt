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
        return shipmentDao.observeShipments().map { shipmentLocalList ->
            shipmentLocalList.map { shipmentLocal ->
                shipmentLocal.toDomain(
                    shipmentLocal.receiverEmail?.let { shipmentDao.getCustomer(it)?.toDomain() },
                    shipmentLocal.senderEmail?.let { shipmentDao.getCustomer(it)?.toDomain() }
                )
            }
        }
    }

    override suspend fun fetchShipments() =
        withContext(Dispatchers.IO) {
            val shipments = shipmentApi.getShipments().map {
                ShipmentLocal(it.toDomain())
            }
            val archivedShipments = shipmentDao.getShipments().filter { it.manualArchive }
            shipmentDao.insertShipments(*shipments.map { shipmentLocal ->
                if (shipmentLocal.number in archivedShipments.map { it.number }) {
                    shipmentLocal.copy(manualArchive = true)
                } else shipmentLocal
            }.toTypedArray())
        }

    override suspend fun archiveShipment(shipmentNumber: String): Unit = withContext(Dispatchers.IO) {
        shipmentDao.getShipment(shipmentNumber)?.let {
            shipmentDao.insertShipments(it.copy(manualArchive = true))
        }
    }
}