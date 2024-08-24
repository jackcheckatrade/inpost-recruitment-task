package pl.inpost.shipment.implementation.presentation.archivedShipmentList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import pl.inpost.design_system.theme.InPostTheme
import pl.inpost.shipment.api.model.ShipmentStatus
import pl.inpost.shipment.implementation.presentation.components.ShipmentCard
import pl.inpost.shipment.implementation.presentation.model.DateTimeDisplayable
import pl.inpost.shipment.implementation.presentation.model.ShipmentDisplayable

@Composable
fun ArchivedShipmentListScreen(
    viewModel: ArchivedShipmentListViewModel
) {
    val viewState by viewModel.viewState.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                viewModel.onStart()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    ArchivedShipmentListScreenContent(
        viewState,
        onMoreClicked = viewModel::onMoreClicked
    )
}

@Composable
fun ArchivedShipmentListScreenContent(
    viewState: ArchivedShipmentList.ViewState,
    onMoreClicked: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(InPostTheme.colorSystem.backgroundPrimary)
            .systemBarsPadding()
    ) {
        items(viewState.shipments) {
            ShipmentCard(
                shipment = it,
                onDetailsButtonClick = { onMoreClicked(it.number) })
            Spacer(modifier = Modifier.height(InPostTheme.dimensSystem.x1))
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ArchivedShipmentListScreenContent(
        viewState = ArchivedShipmentList.ViewState.DEFAULT_STATE.copy(
            shipments = listOf(
                ShipmentDisplayable(
                    "123",
                    ShipmentStatus.DELIVERED, sender = "",
                    statusString = "",
                    expiryDate = DateTimeDisplayable("", "", ""),
                    pickUpDate = DateTimeDisplayable("", "", ""),
                    storedDate = DateTimeDisplayable("", "", ""),
                )
            )
        )
    ) {

    }
}