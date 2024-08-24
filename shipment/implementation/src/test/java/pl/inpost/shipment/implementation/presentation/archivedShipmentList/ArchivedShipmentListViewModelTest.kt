package pl.inpost.shipment.implementation.presentation.archivedShipmentList

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
import pl.inpost.android_common.navigation.Navigator
import pl.inpost.shipment.api.model.ShipmentStatus
import pl.inpost.shipment.api.usecase.ArchiveShipmentUseCase
import pl.inpost.shipment.api.usecase.ObserveGroupedAndSortedShipmentsUseCase
import pl.inpost.shipment.api.usecase.RefreshShipmentsUseCase
import pl.inpost.shipment.implementation.presentation.DateFormatter
import pl.inpost.shipment.implementation.presentation.model.ShipmentDisplayable
import pl.inpost.shipment.implementation.presentation.shipmentList.ShipmentStatusMapper
import pl.inpost.shipment.implementation.utils.MainCoroutineRule
import pl.inpost.shipment.implementation.utils.highlightedShipment
import pl.inpost.shipment.implementation.utils.shipment

@OptIn(ExperimentalCoroutinesApi::class)
class ArchivedShipmentListViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val observeGroupedAndSortedShipmentsUseCase: ObserveGroupedAndSortedShipmentsUseCase =
        mockk()
    private val refreshShipmentsUseCase: RefreshShipmentsUseCase = mockk()
    private val archiveShipmentUseCase: ArchiveShipmentUseCase = mockk()
    private val navigator: Navigator = mockk(relaxed = true)
    private val dateFormatter: DateFormatter = mockk()
    private val shipmentStatusMapper: ShipmentStatusMapper = ShipmentStatusMapper(mockk {
        every { getString(any()) } returns "text"
    })

    private lateinit var viewModel: ArchivedShipmentListViewModel

    @Before
    fun setup() {
        coEvery { refreshShipmentsUseCase.invoke() } returns Result.success(Unit)
        coEvery { archiveShipmentUseCase.invoke(any(), any()) } returns Result.success(Unit)

        viewModel = ArchivedShipmentListViewModel(
            observeGroupedAndSortedShipmentsUseCase = observeGroupedAndSortedShipmentsUseCase,
            archiveShipmentUseCase = archiveShipmentUseCase,
            dateFormatter = dateFormatter,
            shipmentStatusMapper = shipmentStatusMapper,
        )
    }

    @Test
    fun `GIVEN shipments WHEN getShipments THEN viewState contains shipments`() = runTest {
        // given
        coEvery { observeGroupedAndSortedShipmentsUseCase.invoke(true) } returns flowOf(
            mapOf(
                false to listOf(shipment),
                true to listOf(highlightedShipment)
            )
        )

        // when
        viewModel.onStart()

        advanceUntilIdle()

        // then
        viewModel.viewState.value.shipments
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
                ),
                ShipmentDisplayable(
                    number = "2",
                    status = ShipmentStatus.DELIVERED,
                    statusString = "text",
                    pickUpDate = null,
                    expiryDate = null,
                    storedDate = null,
                    sender = ""
                ),
            ), viewModel.viewState.value.shipments
        )
    }

    @Test
    fun `GIVEN shipmentId WHEN onMoreClicked THEN archiveShipmentUseCase with unarchive flag is called`() =
        runTest {
            coEvery { observeGroupedAndSortedShipmentsUseCase.invoke() } returns flowOf(mapOf())

            // given
            val shipmentId = "1"

            // when
            viewModel.onMoreClicked(shipmentId)

            advanceUntilIdle()
            // then
            coVerify { archiveShipmentUseCase.invoke(shipmentId, unarchive = true) }
        }
}