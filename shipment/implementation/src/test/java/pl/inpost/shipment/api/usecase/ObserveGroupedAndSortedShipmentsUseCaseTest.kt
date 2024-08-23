package pl.inpost.shipment.api.usecase

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.inpost.shipment.api.ShipmentRepository
import pl.inpost.shipment.implementation.utils.MainCoroutineRule
import pl.inpost.shipment.implementation.utils.highlightedShipment
import pl.inpost.shipment.implementation.utils.shipment

@OptIn(ExperimentalCoroutinesApi::class)
class ObserveGroupedAndSortedShipmentsUseCaseTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val shipmentRepository: ShipmentRepository = mockk()
    private lateinit var useCase: ObserveGroupedAndSortedShipmentsUseCase

    @Before
    fun setup() {
        coEvery { shipmentRepository.observeShipments() } returns flowOf(emptyList())
        useCase = ObserveGroupedAndSortedShipmentsUseCase(shipmentRepository)
    }

    @Test
    fun `GIVEN empty shipments WHEN getShipments THEN viewState contains empty map`() = runTest {
        // given

        // when
        val result = useCase.invoke()

        advanceUntilIdle()
        // then
        result.test {
            assertEquals(emptyMap<Boolean, String>(), expectMostRecentItem())
        }
    }

    @Test
    fun `GIVEN shipments WHEN getShipments THEN viewState contains map with shipments`() = runTest {
        // given
        coEvery { shipmentRepository.observeShipments() } returns flowOf(
            listOf(shipment, highlightedShipment)
        )
        // when
        val result = useCase.invoke()

        advanceUntilIdle()

        // then
        result.test {
            assertEquals(
                mapOf(
                    false to listOf(shipment),
                    true to listOf(highlightedShipment)
                ),
                expectMostRecentItem()
            )
        }
    }
}