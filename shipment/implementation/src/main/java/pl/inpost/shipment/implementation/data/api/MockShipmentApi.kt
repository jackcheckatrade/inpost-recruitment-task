package pl.inpost.shipment.implementation.data.api

import android.content.Context
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import pl.inpost.shipment.api.model.ShipmentStatus
import pl.inpost.shipment.api.model.ShipmentType
import pl.inpost.shipment.implementation.R
import pl.inpost.shipment.implementation.data.remote.ShipmentNetwork
import pl.inpost.shipment.implementation.data.remote.ShipmentsResponse
import java.time.ZonedDateTime
import kotlin.random.Random

class MockShipmentApi(
    @ApplicationContext private val context: Context,
    private val moshi: Moshi
) : ShipmentApi {

    private val response by lazy {
        val json = context.resources.openRawResource(R.raw.mock_shipment_api_response)
            .bufferedReader()
            .use { it.readText() }

        val jsonAdapter = moshi.adapter(ShipmentsResponse::class.java)


        jsonAdapter.fromJson(json) as ShipmentsResponse
    }
    private var firstUse = true

    override suspend fun getShipments(): List<ShipmentNetwork> {
        delay(1000)
        return if (firstUse) {
            firstUse = false
            emptyList()
        } else {
            response.shipments
        }
    }
}

private fun mockShipmentNetwork(
    number: String = Random.nextLong(1, 9999_9999_9999_9999).toString(),
    type: ShipmentType = ShipmentType.PARCEL_LOCKER,
    status: ShipmentStatus = ShipmentStatus.DELIVERED,
    sender: pl.inpost.shipment.implementation.data.remote.CustomerNetwork? = mockCustomerNetwork(),
    receiver: pl.inpost.shipment.implementation.data.remote.CustomerNetwork? = mockCustomerNetwork(),
    operations: pl.inpost.shipment.implementation.data.remote.OperationsNetwork = mockOperationsNetwork(),
    eventLog: List<pl.inpost.shipment.implementation.data.remote.EventLogNetwork> = emptyList(),
    openCode: String? = null,
    expireDate: ZonedDateTime? = null,
    storedDate: ZonedDateTime? = null,
    pickupDate: ZonedDateTime? = null
) = ShipmentNetwork(
    number = number,
    shipmentType = type.name,
    status = status,
    eventLog = eventLog,
    openCode = openCode,
    expiryDate = expireDate,
    storedDate = storedDate,
    pickUpDate = pickupDate,
    receiver = receiver,
    sender = sender,
    operations = operations
)

private fun mockCustomerNetwork(
    email: String = "name@email.com",
    phoneNumber: String = "123 123 123",
    name: String = "Jan Kowalski"
) = pl.inpost.shipment.implementation.data.remote.CustomerNetwork(
    email = email,
    phoneNumber = phoneNumber,
    name = name
)

private fun mockOperationsNetwork(
    manualArchive: Boolean = false,
    delete: Boolean = false,
    collect: Boolean = false,
    highlight: Boolean = false,
    expandAvizo: Boolean = false,
    endOfWeekCollection: Boolean = false
) = pl.inpost.shipment.implementation.data.remote.OperationsNetwork(
    manualArchive = manualArchive,
    delete = delete,
    collect = collect,
    highlight = highlight,
    expandAvizo = expandAvizo,
    endOfWeekCollection = endOfWeekCollection
)
