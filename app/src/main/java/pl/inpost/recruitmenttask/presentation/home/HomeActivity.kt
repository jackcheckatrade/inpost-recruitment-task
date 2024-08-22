package pl.inpost.recruitmenttask.presentation.home

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import pl.inpost.design_system.theme.InPostTheme
import pl.inpost.shipment.implementation.presentation.shipmentList.ShipmentListScreen
import pl.inpost.shipment.implementation.presentation.shipmentList.ShipmentListViewModel

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
            val shipmentListViewModel: ShipmentListViewModel by viewModels()
            ShipmentListScreen(shipmentListViewModel)
        }
    }
}
