package com.codigomorsa.orderplacing.dto

import com.codigomorsa.orderplacing.types.CustomerInfo


class CustomerInfoDTO(
        val firstName: String,
        val lastName: String,
        val emailAddress: String
) {
    companion object {
        fun toDTO(customer: CustomerInfo): CustomerInfoDTO {
            return CustomerInfoDTO(
                    customer.name.firstName.value,
                    customer.name.lastName.value,
                    customer.email.emailAddress
            )
        }
    }
}
