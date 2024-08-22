package pl.inpost.shipment.implementation.di

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.inpost.shipment.implementation.data.api.ApiTypeAdapter
import pl.inpost.shipment.implementation.data.api.MockShipmentApi
import pl.inpost.shipment.implementation.data.api.ShipmentApi
import pl.inpost.shipment.implementation.data.api.ShipmentStatusJsonAdapter

@InstallIn(SingletonComponent::class)
@Module
class ShipmentNetworkModule {
    @Provides
    fun shipmentApi(
        @ApplicationContext context: Context,
        moshi: Moshi
    ): ShipmentApi =
        MockShipmentApi(context, moshi)

    @Provides
    fun provideMoshi(apiTypeAdapter: ApiTypeAdapter): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(apiTypeAdapter)
        .add(ShipmentStatusJsonAdapter())
        .build()
}