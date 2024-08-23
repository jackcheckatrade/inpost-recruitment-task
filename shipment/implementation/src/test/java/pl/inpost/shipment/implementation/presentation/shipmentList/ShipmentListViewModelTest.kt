package pl.inpost.shipment.implementation.presentation.shipmentList

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.inpost.shipment.api.model.Shipment
import pl.inpost.shipment.api.model.ShipmentStatus
import pl.inpost.shipment.api.usecase.ArchiveShipmentUseCase
import pl.inpost.shipment.api.usecase.ObserveGroupedAndSortedShipmentsUseCase
import pl.inpost.shipment.api.usecase.RefreshShipmentsUseCase
import pl.inpost.shipment.implementation.presentation.DateFormatter
import pl.inpost.shipment.implementation.presentation.model.ShipmentDisplayable
import pl.inpost.shipment.implementation.utils.MainCoroutineRule
import pl.inpost.shipment.implementation.utils.highlightedShipment
import pl.inpost.shipment.implementation.utils.shipment

@OptIn(ExperimentalCoroutinesApi::class)
class ShipmentListViewModelTest {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val observeGroupedAndSortedShipmentsUseCase: ObserveGroupedAndSortedShipmentsUseCase =
        mockk()
    private val refreshShipmentsUseCase: RefreshShipmentsUseCase = mockk()
    private val archiveShipmentUseCase: ArchiveShipmentUseCase = mockk()
    private val dateFormatter: DateFormatter = mockk()
    private val shipmentStatusMapper: ShipmentStatusMapper = ShipmentStatusMapper(mockk {
        every { getString(any()) } returns "text"
    })

    private lateinit var viewModel: ShipmentListViewModel

    @Before
    fun setup() {
        coEvery { observeGroupedAndSortedShipmentsUseCase.invoke() } returns flowOf(mapOf())
        coEvery { refreshShipmentsUseCase.invoke() } returns Result.success(Unit)
        coEvery { archiveShipmentUseCase.invoke(any()) } returns Result.success(Unit)

        viewModel = ShipmentListViewModel(
            observeGroupedAndSortedShipmentsUseCase = observeGroupedAndSortedShipmentsUseCase,
            refreshShipmentsUseCase = refreshShipmentsUseCase,
            archiveShipmentUseCase = archiveShipmentUseCase,
            dateFormatter = dateFormatter,
            shipmentStatusMapper = shipmentStatusMapper
        )
    }

    @Test
    fun `GIVEN empty shipments WHEN getShipments THEN viewState contains empty lists`() = runTest {
        // given
        coEvery { observeGroupedAndSortedShipmentsUseCase.invoke() } returns flowOf(mapOf())

        // when
        viewModel.getShipments()

        advanceUntilIdle()

        // then
        assertEquals(emptyList<Shipment>(), viewModel.viewState.value.shipments)
        assertEquals(emptyList<Shipment>(), viewModel.viewState.value.highlightedShipments)
    }

    @Test
    fun `GIVEN shipments WHEN getShipments THEN viewState contains shipments`() = runTest {
        // given
        coEvery { observeGroupedAndSortedShipmentsUseCase.invoke() } returns flowOf(
            mapOf(
                false to listOf(shipment),
                true to listOf(highlightedShipment)
            )
        )

        // when
        val viewModel = ShipmentListViewModel(
            observeGroupedAndSortedShipmentsUseCase = observeGroupedAndSortedShipmentsUseCase,
            refreshShipmentsUseCase = refreshShipmentsUseCase,
            archiveShipmentUseCase = archiveShipmentUseCase,
            dateFormatter = dateFormatter,
            shipmentStatusMapper = shipmentStatusMapper
        )

        viewModel.onStart()

        advanceUntilIdle()

        // then
        viewModel.viewState.value.shipments
        viewModel.viewState.value.highlightedShipments
        assertEquals(
            listOf(
                ShipmentDisplayable(
                    number = "1",
                    status = ShipmentStatus.DELIVERED,
                    statusString = "text",
                    pickUpDate = null,
                    expiryDate = null,
                    storedDate = null,
                    sender = ""
                )
            ), viewModel.viewState.value.shipments
        )
        assertEquals(
            listOf(
                ShipmentDisplayable(
                    number = "2",
                    status = ShipmentStatus.DELIVERED,
                    statusString = "text",
                    pickUpDate = null,
                    expiryDate = null,
                    storedDate = null,
                    sender = ""
                )
            ), viewModel.viewState.value.highlightedShipments
        )
    }

    @Test
    fun `GIVEN shipmentId WHEN archiveShipment THEN archiveShipmentUseCase is called`() = runTest {
        // given
        val shipmentId = "1"

        // when
        viewModel.archiveShipment(shipmentId)

        advanceUntilIdle()
        // then
        coVerify { archiveShipmentUseCase.invoke(shipmentId) }
    }
}