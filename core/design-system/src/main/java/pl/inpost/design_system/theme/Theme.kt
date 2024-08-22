package pl.inpost.design_system.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

@Composable
fun InPostTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) InPostColorSystem.dark else InPostColorSystem.light

    val typography = InPostTypographySystem.default

    val dimens = InPostDimensSystem.default

    CompositionLocalProvider(
        LocalColorSystem provides colors,
        LocalTypographySystem provides typography,
        LocalDimensSystem provides dimens,
        content = content,
    )
}

object InPostTheme {
    val colorSystem: InPostColorSystem
        @Composable
        get() = LocalColorSystem.current
    val typographySystem: InPostTypographySystem
        @Composable
        get() = LocalTypographySystem.current
    val dimensSystem: InPostDimensSystem
        @Composable
        get() = LocalDimensSystem.current
}

/** Static Compositions Local */

val LocalColorSystem = staticCompositionLocalOf {
    InPostColorSystem.light
}

val LocalTypographySystem = staticCompositionLocalOf {
    InPostTypographySystem.default
}

val LocalDimensSystem = staticCompositionLocalOf {
    InPostDimensSystem.default
}