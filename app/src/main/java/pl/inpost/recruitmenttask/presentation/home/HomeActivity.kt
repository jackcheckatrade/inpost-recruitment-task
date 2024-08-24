package pl.inpost.recruitmenttask.presentation.home

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import pl.inpost.android_common.navigation.NavigationEvent
import pl.inpost.android_common.navigation.Navigator
import pl.inpost.android_common.navigation.Screen
import pl.inpost.design_system.theme.InPostTheme
import pl.inpost.shipment.implementation.presentation.archivedShipmentList.ArchivedShipmentListScreen
import pl.inpost.shipment.implementation.presentation.archivedShipmentList.ArchivedShipmentListViewModel
import pl.inpost.shipment.implementation.presentation.shipmentList.ShipmentListScreen
import pl.inpost.shipment.implementation.presentation.shipmentList.ShipmentListViewModel
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val navController = rememberNavController()

            LaunchedEffect(key1 = Unit) {
                repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    navigator.navigationEvents.collectLatest { navigationEvent ->
                        when (navigationEvent) {
                            NavigationEvent.Back -> {
                                if (navController.previousBackStackEntry == null) {
                                    this@HomeActivity.finish()
                                } else {
                                    navController.navigateUp()
                                }
                            }

                            is NavigationEvent.Destination -> {
                                navController.navigate(
                                    route = navigationEvent.screen.route,
                                )
                            }
                        }
                    }
                }
            }
            HomeActivityContent(navController)
        }
    }

    @Composable
    fun HomeActivityContent(
        navController: NavHostController
    ) {
        InPostTheme {
            NavHost(
                navController = navController,
                startDestination = Navigator.START_DESTINATION.route
            ) {
                composable(
                    route = Screen.ShipmentList.route
                ) {
                    val shipmentListViewModel: ShipmentListViewModel by viewModels()
                    ShipmentListScreen(shipmentListViewModel)
                }

                composable(
                    route = Screen.ArchivedShipmentList.route
                ) {
                    val archivedShipmentListViewModel: ArchivedShipmentListViewModel by viewModels()
                    ArchivedShipmentListScreen(
                        viewModel = archivedShipmentListViewModel
                    )
                }
            }
        }
    }
}
