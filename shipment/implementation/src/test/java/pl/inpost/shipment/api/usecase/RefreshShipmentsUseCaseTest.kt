package pl.inpost.shipment.api.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import pl.inpost.shipment.api.ShipmentRepository

@OptIn(ExperimentalCoroutinesApi::class)
class RefreshShipmentsUseCaseTest {

    private val shipmentRepository: ShipmentRepository = mockk()

    private lateinit var usecase: RefreshShipmentsUseCase

    @Before
    fun setup() {
        coEvery { shipmentRepository.fetchShipments() } returns Unit

        usecase = RefreshShipmentsUseCase(shipmentRepository)
    }

    @Test
    fun `refreshShipments should call repository fetchShipments`() = runTest {
        // when
        usecase()
        advanceUntilIdle()

        // then
        coVerify { shipmentRepository.fetchShipments() }
    }


    @Test
    fun `refreshShipments should return Failure`() = runTest {
        // given
        coEvery { shipmentRepository.fetchShipments() } throws Exception()

        // when
        val result = usecase()
        advanceUntilIdle()

        // then
        assert(result.isFailure)
    }
}