package pl.inpost.shipment.implementation.presentation.shipmentList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.inpost.design_system.component.appbar.SimpleAppBar
import pl.inpost.design_system.component.button.DetailArrowButton
import pl.inpost.design_system.component.divider.HorizontalDivider
import pl.inpost.design_system.theme.InPostTheme
import pl.inpost.shipment.api.model.ShipmentStatus
import pl.inpost.shipment.implementation.R
import pl.inpost.shipment.implementation.presentation.model.DateTimeDisplayable
import pl.inpost.shipment.implementation.presentation.model.ShipmentDisplayable


@Composable
fun ShipmentListScreen(
    viewModel: ShipmentListViewModel
) {
    val viewState by viewModel.viewState.collectAsState()
    ShipmentListScreenContent(
        viewState,
        onPullToRefresh = viewModel::refresh
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShipmentListScreenContent(
    viewState: ShipmentList.ViewState,
    onPullToRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            SimpleAppBar(title = stringResource(id = R.string.app_name))
        },
        modifier = modifier
    ) { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
        ) {
            val refreshState = rememberPullToRefreshState()
            PullToRefreshBox(
                isRefreshing = viewState.isSwipeRefreshing,
                onRefresh = onPullToRefresh,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center,
                state = refreshState,
                indicator = {
                    Indicator(
                        modifier = Modifier.align(Alignment.TopCenter),
                        isRefreshing = viewState.isSwipeRefreshing,
                        state = refreshState,
                        containerColor = InPostTheme.colorSystem.backgroundPrimary,
                        color = InPostTheme.colorSystem.accentPrimary
                    )
                }
            ) {
                if (viewState.isLoading) {
                    CircularProgressIndicator(
                        color = InPostTheme.colorSystem.accentPrimary,
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .background(color = InPostTheme.colorSystem.backgroundPrimary)
                            .fillMaxSize()
                    ) {
                        if (viewState.isEmpty()) {
                            item {
                                Text(
                                    text = stringResource(id = R.string.no_shipments),
                                    style = InPostTheme.typographySystem.value.copy(textAlign = TextAlign.Center),
                                    modifier = Modifier
                                        .padding(InPostTheme.dimensSystem.x4)
                                        .fillMaxWidth()
                                )
                            }
                        }
                        if (viewState.highlightedShipments.isNotEmpty())
                            item {
                                HorizontalDivider(
                                    title = stringResource(id = R.string.status_ready_to_pickup),
                                    modifier = Modifier.padding(vertical = InPostTheme.dimensSystem.x4)
                                )
                            }
                        itemsIndexed(viewState.highlightedShipments) { index, shipment ->
                            ShipmentCard(
                                shipment = shipment,
                                onDetailsButtonClick = {}
                            )
                            if (index < viewState.highlightedShipments.size - 1)
                                Spacer(modifier = Modifier.height(InPostTheme.dimensSystem.x4))
                        }
                        if (viewState.shipments.isNotEmpty())
                            item {
                                HorizontalDivider(
                                    title = stringResource(id = R.string.group_other),
                                    modifier = Modifier.padding(vertical = InPostTheme.dimensSystem.x4)
                                )
                            }
                        items(viewState.shipments) { shipment ->
                            ShipmentCard(
                                shipment = shipment,
                                onDetailsButtonClick = {}
                            )
                            Spacer(modifier = Modifier.height(InPostTheme.dimensSystem.x4))
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun ShipmentCard(
    shipment: ShipmentDisplayable,
    onDetailsButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RectangleShape,
        colors = CardDefaults.elevatedCardColors(
            containerColor = InPostTheme.colorSystem.backgroundSurface
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = InPostTheme.dimensSystem.x1)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = InPostTheme.dimensSystem.x5,
                    vertical = InPostTheme.dimensSystem.x4
                ),
            verticalArrangement = Arrangement.spacedBy(InPostTheme.dimensSystem.x4)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                TitleWithValue(
                    title = stringResource(id = R.string.parcel_number_header),
                    value = shipment.number,
                    bold = false
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_paczkomat),
                    contentDescription = null,
                )
            }

            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                TitleWithValue(
                    title = stringResource(id = R.string.status_header),
                    value = shipment.statusString
                )

                if (shipment.isDateVisible()) {
                    val headerString = when (shipment.status) {
                        ShipmentStatus.READY_TO_PICKUP -> stringResource(id = R.string.ready_to_pickup_date_header)
                        ShipmentStatus.DELIVERED -> stringResource(id = R.string.delivered_date_header)
                        else -> null
                    }

                    val dateTimeDisplayable = when (shipment.status) {
                        ShipmentStatus.READY_TO_PICKUP -> shipment.expiryDate
                        ShipmentStatus.DELIVERED -> shipment.pickUpDate
                        else -> null
                    }
                    StatusWithDate(
                        status = headerString.orEmpty(),
                        dayOfWeekShort = dateTimeDisplayable?.dayOfWeekShort.orEmpty(),
                        dateString = dateTimeDisplayable?.date.orEmpty(),
                        hourString = dateTimeDisplayable?.time.orEmpty(),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                TitleWithValue(
                    title = stringResource(id = R.string.sender_header), value = shipment.sender
                )
                DetailArrowButton(onClick = onDetailsButtonClick)
            }
        }
    }
}

@Composable
fun TitleWithValue(
    title: String,
    value: String,
    bold: Boolean = true,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(
            text = title.uppercase(),
            style = InPostTheme.typographySystem.header.copy(InPostTheme.colorSystem.textSecondary),
        )
        Text(
            text = value,
            style = if (bold) InPostTheme.typographySystem.value
            else InPostTheme.typographySystem.valueSecondary.copy(InPostTheme.colorSystem.textPrimary),
        )
    }
}

@Composable
fun StatusWithDate(
    modifier: Modifier = Modifier,
    status: String,
    dayOfWeekShort: String,
    dateString: String,
    hourString: String
) {
    Column(
        modifier,
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = status.uppercase(),
            style = InPostTheme.typographySystem.header.copy(InPostTheme.colorSystem.textSecondary),
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(InPostTheme.dimensSystem.x1),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = dayOfWeekShort,
                style = InPostTheme.typographySystem.valueSecondary
            )
            VerticalDivider(
                color = InPostTheme.colorSystem.textTertiary,
                thickness = 1.dp,
                modifier = Modifier
                    .height(15.dp)
            )
            Text(
                text = dateString,
                style = InPostTheme.typographySystem.valueSecondary
            )
            VerticalDivider(
                color = InPostTheme.colorSystem.textTertiary,
                thickness = 1.dp,
                modifier = Modifier
                    .height(15.dp)
            )
            Text(
                text = hourString,
                style = InPostTheme.typographySystem.valueSecondary
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ShipmentListScreenPreview() {
    ShipmentListScreenContent(
        ShipmentList.ViewState.DEFAULT_STATE.copy(
            highlightedShipments = listOf(
                ShipmentDisplayable(
                    number = "123456789",
                    status = ShipmentStatus.READY_TO_PICKUP,
                    statusString = "Ready to collect",
                    sender = "Sender name 1",
                    pickUpDate = DateTimeDisplayable(
                        dayOfWeekShort = "Mon",
                        date = "12.12.2021",
                        time = "12:00"
                    ),
                    expiryDate = DateTimeDisplayable(
                        dayOfWeekShort = "Mon",
                        date = "12.12.2021",
                        time = "12:00"
                    ),
                    storedDate = DateTimeDisplayable(
                        dayOfWeekShort = "Mon",
                        date = "12.12.2021",
                        time = "12:00"
                    ),
                )
            ),
            shipments = listOf(
                ShipmentDisplayable(
                    number = "123456789",
                    status = ShipmentStatus.READY_TO_PICKUP,
                    statusString = "Ready to collect",
                    sender = "Sender name 2",
                    pickUpDate = DateTimeDisplayable(
                        dayOfWeekShort = "Mon",
                        date = "12.12.2021",
                        time = "12:00"
                    ),
                    expiryDate = DateTimeDisplayable(
                        dayOfWeekShort = "Mon",
                        date = "12.12.2021",
                        time = "12:00"
                    ),
                    storedDate = DateTimeDisplayable(
                        dayOfWeekShort = "Mon",
                        date = "12.12.2021",
                        time = "12:00"
                    ),
                )
            )
        ),
        onPullToRefresh = {}
    )
}