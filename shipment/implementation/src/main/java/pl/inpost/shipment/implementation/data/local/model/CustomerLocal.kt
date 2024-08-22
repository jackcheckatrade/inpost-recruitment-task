package pl.inpost.shipment.implementation.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.inpost.shipment.api.model.Customer

@Entity(tableName = "customers")
data class CustomerLocal(
    @PrimaryKey
    val email: String,
    val phoneNumber: String?,
    val name: String?,
){
    constructor(customer: Customer) : this(
        email = customer.email.orEmpty(),
        phoneNumber = customer.phoneNumber,
        name = customer.name
    )

    fun toDomain() = Customer(
        email = email,
        phoneNumber = phoneNumber,
        name = name
    )
}
