package pl.inpost.android_common.navigation

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator @Inject constructor() {

    companion object {
        val START_DESTINATION = Screen.ShipmentList
    }

    private val _navigationEvents =
        Channel<NavigationEvent>(Channel.RENDEZVOUS)
    val navigationEvents = _navigationEvents.receiveAsFlow()

    fun navigateTo(
        screen: Screen
    ) {
        _navigationEvents.trySend(
            NavigationEvent.Destination(
                screen
            ),
        )
    }

    fun navigateBack() {
        _navigationEvents.trySend(NavigationEvent.Back)
    }
}

sealed interface NavigationEvent {
    data class Destination(
        val screen: Screen
    ) : NavigationEvent

    data object Back : NavigationEvent
}

sealed class Screen(val route: String) {
    data object ShipmentList : Screen("shipmentList")
    data object ArchivedShipmentList : Screen("archivedShipments")
}
