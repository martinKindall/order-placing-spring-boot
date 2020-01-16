package com.codigomorsa.orderplacing.dto

import com.codigomorsa.orderplacing.types.Address


class AddressDTO(
        val address: String,
        val city: String,
        val zipCode: String
) {
    companion object {
        fun toDTO(address: Address): AddressDTO {
            return AddressDTO(
                    address.address.value,
                    address.city.value,
                    address.zipCode.value
            )
        }
    }
}
