package pl.inpost.shipment.implementation.presentation.shipmentList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.inpost.design_system.component.appbar.SimpleAppBar
import pl.inpost.design_system.component.button.DetailArrowButton
import pl.inpost.design_system.component.divider.HorizontalDivider
import pl.inpost.design_system.theme.InPostTheme
import pl.inpost.shipment.implementation.R


@Composable
fun ShipmentListScreen() {
    ShipmentListScreenContent()
}

@Composable
fun ShipmentListScreenContent(
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            SimpleAppBar(title = stringResource(id = R.string.app_name))
        },
        modifier = modifier
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .background(color = InPostTheme.colorSystem.backgroundPrimary)
        ) {
            item {
                HorizontalDivider(
                    title = "Your shipments",
                    modifier = Modifier.padding(vertical = InPostTheme.dimensSystem.x4)
                )
            }
            items(10) {
                ShipmentCard(
                    parcelNumber = "123456789",
                    status = "Delivered",
                    sender = "John Doe",
                    dayOfWeekShort = "Mon",
                    dateString = "12.12.2021",
                    hourString = "12:00",
                    onDetailsButtonClick = {}
                )
                Spacer(modifier = Modifier.height(InPostTheme.dimensSystem.x4))
            }
        }
    }

}


@Composable
fun ShipmentCard(
    parcelNumber: String,
    status: String,
    sender: String,
    dayOfWeekShort: String,
    dateString: String,
    hourString: String,
    onDetailsButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
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
                    title = "Parcel number", value = parcelNumber, bold = false
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
                    title = "Status", value = status
                )
                StatusWithDate(
                    status = status,
                    dayOfWeekShort = dayOfWeekShort,
                    dateString = dateString,
                    hourString = hourString,
                    modifier = Modifier.weight(1f)
                )
            }
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                TitleWithValue(
                    title = "Sender", value = sender
                )
                DetailArrowButton()
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
    ShipmentListScreenContent()
}