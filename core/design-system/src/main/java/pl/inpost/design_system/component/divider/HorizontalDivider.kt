package pl.inpost.design_system.component.divider

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.inpost.design_system.theme.InPostTheme

@Composable
fun HorizontalDivider(title: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(1.dp)
                .background(color = InPostTheme.colorSystem.divider)
        )

        Text(
            text = title,
            style = InPostTheme.typographySystem.divider
                .copy(color = InPostTheme.colorSystem.textTertiary),
            modifier = Modifier.padding(horizontal = InPostTheme.dimensSystem.x4)
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .height(1.dp)
                .background(color = InPostTheme.colorSystem.divider)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    HorizontalDivider(title = "Preview")
}