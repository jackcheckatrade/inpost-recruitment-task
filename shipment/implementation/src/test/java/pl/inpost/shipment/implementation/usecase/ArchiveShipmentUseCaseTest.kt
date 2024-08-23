package pl.inpost.shipment.implementation.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.inpost.shipment.api.ShipmentRepository
import pl.inpost.shipment.api.usecase.ArchiveShipmentUseCase
import pl.inpost.shipment.implementation.utils.MainCoroutineRule

@OptIn(ExperimentalCoroutinesApi::class)
class ArchiveShipmentUseCaseTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val shipmentRepository: ShipmentRepository = mockk()

    private lateinit var usecase: ArchiveShipmentUseCase

    @Before
    fun setup() {
        coEvery { shipmentRepository.archiveShipment(any()) } returns Unit
        usecase = ArchiveShipmentUseCase(shipmentRepository)
    }

    @Test
    fun `archiveShipment should call repository archiveShipment`() = runTest {
        // given
        val shipmentId = "shipmentId"

        // when
        usecase(shipmentId)
        advanceUntilIdle()

        // then
        coVerify { shipmentRepository.archiveShipment(shipmentId) }
    }
}