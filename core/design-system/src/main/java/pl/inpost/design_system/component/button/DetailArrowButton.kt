package pl.inpost.design_system.component.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import pl.inpost.design_system.R
import pl.inpost.design_system.theme.InPostTheme

@Composable
fun DetailArrowButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Row(
        modifier
            .padding(
                vertical = InPostTheme.dimensSystem.x1
            )
            .clickable {
                onClick()
            },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.more),
            style = InPostTheme.typographySystem.button
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow),
            contentDescription = "button",
            tint = InPostTheme.colorSystem.accentPrimary
        )
    }
}

@Preview
@Composable
private fun Preview() {
    DetailArrowButton()
}