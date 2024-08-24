package pl.inpost.design_system.component.button

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import pl.inpost.design_system.theme.InPostTheme

@Composable
fun PrimaryButton(
    title: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            contentColor = InPostTheme.colorSystem.textPrimary,
            containerColor = InPostTheme.colorSystem.accentPrimary
        ),
        shape = RectangleShape,
        modifier = modifier
    ) {
        Text(text = title, style = InPostTheme.typographySystem.value)
    }
}

@Preview
@Composable
private fun Preview() {
    PrimaryButton(title = "Test") {

    }
}