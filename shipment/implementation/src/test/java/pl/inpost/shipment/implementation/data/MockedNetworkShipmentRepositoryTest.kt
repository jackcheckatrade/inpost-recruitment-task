@file:OptIn(ExperimentalCoroutinesApi::class)

package pl.inpost.shipment.implementation.data

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import pl.inpost.shipment.api.ShipmentRepository
import pl.inpost.shipment.api.model.Shipment
import pl.inpost.shipment.implementation.data.local.ShipmentDao
import pl.inpost.shipment.implementation.data.local.model.ShipmentLocal
import pl.inpost.shipment.implementation.data.remote.api.ShipmentApi
import pl.inpost.shipment.implementation.data.remote.model.ShipmentNetwork
import pl.inpost.shipment.implementation.utils.shipment

class MockedNetworkShipmentRepositoryTest {

    private val shipmentApi: ShipmentApi = mockk()
    private val shipmentDao: ShipmentDao = mockk(relaxed = true)
    private lateinit var shipmentRepository: ShipmentRepository

    @Before
    fun setup() {
        shipmentRepository = MockedNetworkShipmentRepository(shipmentApi, shipmentDao)
    }

    @Test
    fun `GIVEN empty shipments WHEN observeShipments THEN empty list`() = runTest {
        // given
        coEvery { shipmentApi.getShipments() } returns emptyList()
        coEvery { shipmentDao.observeShipments() } returns flowOf(emptyList())

        // when
        val result = shipmentRepository.observeShipments()

        // then
        result.test {
            assertEquals(emptyList<Shipment>(), expectMostRecentItem())
        }
    }

    @Test
    fun `GIVEN shipments WHEN observeShipments THEN list with shipments`() = runTest {
        // given
        val shipments = listOf(
            shipment.copy(number = "1"),
            shipment.copy(number = "2"),
            shipment.copy(number = "3")
        )
        val shipmentNetworks = shipments.map { ShipmentNetwork(it) }
        val shipmentLocals = shipments.map { ShipmentLocal(it) }
        coEvery { shipmentApi.getShipments() } returns shipmentNetworks
        coEvery { shipmentDao.observeShipments() } returns flowOf(shipmentLocals)

        // when
        val result = shipmentRepository.observeShipments()

        // then
        result.test {
            assertEquals(shipments, expectMostRecentItem())
        }
    }

    @Test
    fun `GIVEN shipments WHEN archiveShipment THEN remember archive flags`() = runTest {
        // given
        val shipments = listOf(
            shipment.copy(
                number = "1",
                operations = shipment.operations.copy(manualArchive = true)
            ),
            shipment.copy(number = "2"),
            shipment.copy(number = "3")
        )
        val shipmentNetworks =
            shipments.map {
                ShipmentNetwork(
                    it.copy(
                        operations = it.operations.copy(manualArchive = false)
                    )
                )
            }
        val shipmentLocals = shipments.map { ShipmentLocal(it) }
        coEvery { shipmentApi.getShipments() } returns shipmentNetworks
        coEvery { shipmentDao.getShipments() } returns shipmentLocals
        // when
        shipmentRepository.fetchShipments()

        advanceUntilIdle()

        coVerify {
            shipmentDao.insertShipments(
                *shipmentLocals.toTypedArray()
            )
        }
    }
}