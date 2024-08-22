package pl.inpost.shipment.implementation.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.inpost.shipment.implementation.data.api.ApiTypeAdapter
import pl.inpost.shipment.implementation.data.api.MockShipmentApi
import pl.inpost.shipment.implementation.data.api.ShipmentApi

@InstallIn(SingletonComponent::class)
@Module
class ShipmentNetworkModule {
    @Provides
    fun shipmentApi(
        @ApplicationContext context: Context,
        apiTypeAdapter: ApiTypeAdapter
    ): ShipmentApi =
        MockShipmentApi(context, apiTypeAdapter)
}