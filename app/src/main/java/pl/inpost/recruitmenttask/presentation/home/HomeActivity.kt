package pl.inpost.recruitmenttask.presentation.home

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import pl.inpost.design_system.theme.InPostTheme
import pl.inpost.shipment.implementation.presentation.shipmentList.ShipmentListScreen

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            HomeActivityContent()
        }
    }

    @Composable
    fun HomeActivityContent() {
        InPostTheme {
            ShipmentListScreen()
        }
    }
}
