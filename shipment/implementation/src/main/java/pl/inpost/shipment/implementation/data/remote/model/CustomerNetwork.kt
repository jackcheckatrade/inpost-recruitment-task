package pl.inpost.shipment.implementation.data.remote.model

import pl.inpost.shipment.api.model.Customer

data class CustomerNetwork(
    val email: String?,
    val phoneNumber: String?,
    val name: String?
) {
    constructor(customer: Customer) : this(
        customer.email,
        customer.phoneNumber,
        customer.name
    )

    fun toDomain() = Customer(
        email = email,
        phoneNumber = phoneNumber,
        name = name
    )
}
