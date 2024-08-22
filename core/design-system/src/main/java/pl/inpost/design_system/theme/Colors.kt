package pl.inpost.design_system.theme

import androidx.compose.ui.graphics.Color
import javax.annotation.concurrent.Immutable

val yellow = Color(0xFFFFCD00)
val black = Color(0xFF404041)
val grey4 = Color(0xFF929497)
val grey3 = Color(0xFFBBBDBF)
val grey2 = Color(0xFFE9E9E9)
val grey1 = Color(0xFFF2F2F2)

@Immutable
data class InPostColorSystem(
    val backgroundPrimary: Color,
    val backgroundSurface: Color,
    val accentPrimary: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textTertiary: Color,
    val divider: Color
) {
    companion object {
        val light = InPostColorSystem(
            backgroundPrimary = grey1,
            backgroundSurface = Color.White,
            accentPrimary = yellow,
            textPrimary = black,
            textSecondary = grey4,
            textTertiary = grey3,
            divider = grey2
        )

        // TODO: Define dark mode color when will be ready
        val dark = InPostColorSystem(
            backgroundPrimary = grey1,
            backgroundSurface = Color.White,
            accentPrimary = yellow,
            textPrimary = black,
            textSecondary = grey4,
            textTertiary = grey3,
            divider = grey2
        )
    }
}