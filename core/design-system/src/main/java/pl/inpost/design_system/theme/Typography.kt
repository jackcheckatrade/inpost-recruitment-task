package pl.inpost.design_system.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import pl.inpost.design_system.R
import javax.annotation.concurrent.Immutable

val montserratFamily = FontFamily(
    Font(
        R.font.montserrat_light,
        style = FontStyle.Normal,
        weight = FontWeight.Light
    ),
    Font(
        R.font.montserrat_regular,
        style = FontStyle.Normal,
        weight = FontWeight.Normal
    ),
    Font(
        R.font.montserrat_medium,
        style = FontStyle.Normal,
        weight = FontWeight.Medium
    ),
    Font(
        R.font.montserrat_semibold,
        style = FontStyle.Normal,
        weight = FontWeight.SemiBold
    ),
    Font(
        R.font.montserrat_bold,
        style = FontStyle.Normal,
        weight = FontWeight.Bold
    ),
    Font(
        R.font.montserrat_extrabold,
        style = FontStyle.Normal,
        weight = FontWeight.ExtraBold
    ),
    Font(
        R.font.montserrat_black,
        style = FontStyle.Normal,
        weight = FontWeight.Black
    )
)

/*
Figma typography name to InPostTypographySystem:
    subtitle -> header
    h7 -> divider
    status -> value
    h6 -> valueSecondary
    h9 -> button
 */

@Immutable
data class InPostTypographySystem(
    val header: TextStyle,
    val value: TextStyle,
    val valueSecondary: TextStyle,
    val divider: TextStyle,
    val button: TextStyle
) {
    companion object {
        val default = InPostTypographySystem(
            header = TextStyle(
                fontSize = 11.sp,
                lineHeight = 16.sp,
                fontFamily = montserratFamily,
                fontWeight = FontWeight.SemiBold
            ),
            value = TextStyle(
                fontSize = 15.sp,
                lineHeight = 24.sp,
                fontFamily = montserratFamily,
                fontWeight = FontWeight.Bold
            ),
            valueSecondary = TextStyle(
                fontSize = 15.sp,
                lineHeight = 24.sp,
                fontFamily = montserratFamily,
                fontWeight = FontWeight.Normal
            ),
            divider = TextStyle(
                fontSize = 13.sp,
                lineHeight = 16.sp,
                fontFamily = montserratFamily,
                fontWeight = FontWeight.SemiBold
            ),
            button = TextStyle(
                fontSize = 12.sp,
                lineHeight = 16.sp,
                fontFamily = montserratFamily,
                fontWeight = FontWeight.Bold
            ),
        )
    }
}