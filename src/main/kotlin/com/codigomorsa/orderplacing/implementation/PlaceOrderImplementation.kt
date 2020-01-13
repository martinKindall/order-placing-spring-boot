package com.codigomorsa.orderplacing.implementation

import com.codigomorsa.orderplacing.types.*
import kotlin.IllegalArgumentException

fun toCustomerInfo(
        unvalidatedCustomerInfo: UnvalidatedCustomerInfo): CustomerInfo {
    val firstName = String50.create(unvalidatedCustomerInfo.firstName)
    val lastName = String50.create(unvalidatedCustomerInfo.lastName)
    val email = EmailAddress.create(unvalidatedCustomerInfo.email)

    if (firstName is Success && lastName is Success && email is Success) {
        return CustomerInfo(
                PersonalName(firstName.value, lastName.value),
                email.value
        )
    } else {
        listOf(firstName, lastName, email).filter {
            it is Failure
        }.first().let {
            throw (it as? Failure)?.reason?: IllegalArgumentException("Unknown Reason")
        }
    }
}