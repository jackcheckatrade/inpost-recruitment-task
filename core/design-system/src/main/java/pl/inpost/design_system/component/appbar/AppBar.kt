package pl.inpost.design_system.component.appbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pl.inpost.design_system.theme.InPostTheme

@Composable
fun SimpleAppBar(
    title: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(color = InPostTheme.colorSystem.backgroundSurface)
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(
                horizontal = InPostTheme.dimensSystem.x5,
                vertical = InPostTheme.dimensSystem.x4
            )
    ) {
        Text(
            text = title,
            style = InPostTheme.typographySystem.value,
            modifier = Modifier.align(Alignment.CenterStart)
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFDDDDDD,
)
@Composable
private fun Preview() {
    SimpleAppBar(title = "Preview")
}