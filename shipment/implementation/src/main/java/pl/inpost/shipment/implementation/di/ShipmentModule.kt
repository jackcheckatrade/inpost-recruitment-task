package pl.inpost.shipment.implementation.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.inpost.shipment.api.ShipmentRepository
import pl.inpost.shipment.implementation.data.MockedNetworkShipmentRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class ShipmentModule {
    @Binds
    abstract fun bindShipmentRepository(
        mockedNetworkShipmentRepository: MockedNetworkShipmentRepository
    ): ShipmentRepository
}