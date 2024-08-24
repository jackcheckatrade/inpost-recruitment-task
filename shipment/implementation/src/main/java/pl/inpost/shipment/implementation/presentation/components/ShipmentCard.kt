package pl.inpost.shipment.implementation.presentation.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pl.inpost.design_system.component.button.DetailArrowButton
import pl.inpost.design_system.theme.InPostTheme
import pl.inpost.shipment.api.model.ShipmentStatus
import pl.inpost.shipment.implementation.R
import pl.inpost.shipment.implementation.presentation.model.ShipmentDisplayable


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
    modifier: Modifier = Modifier,
    bold: Boolean = true,
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