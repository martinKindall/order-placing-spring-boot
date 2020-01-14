package com.codigomorsa.orderplacing.implementation

import com.codigomorsa.orderplacing.Utils.SwitchFun
import com.codigomorsa.orderplacing.Utils.bind
import com.codigomorsa.orderplacing.types.*
import kotlin.IllegalArgumentException
import com.codigomorsa.orderplacing.types.Result
import java.lang.Exception

typealias CheckProductCodeExists = (x: ProductCode) -> Boolean

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
        throw getErrorFromListOfResults(listOf(firstName, lastName, email))
    }
}

fun toAddress(unvalidatedAddress: UnvalidatedAddress): Address {
    val address = String50.create(unvalidatedAddress.address)
    val city = String50.create(unvalidatedAddress.city)
    val zipCode = String50.create(unvalidatedAddress.zipCode)

    if (address is Success && city is Success && zipCode is Success ) {
        return Address(
                address.value,
                city.value,
                zipCode.value
        )
    } else {
        throw getErrorFromListOfResults(listOf(address, city, zipCode))
    }
}

fun toProductCode(
        checkProductCodeExists: CheckProductCodeExists,
        productCode: String): Result<ProductCode, Exception> {
    val checkProduct: SwitchFun<ProductCode, ProductCode> = { aProductCode: ProductCode ->
        if (checkProductCodeExists(aProductCode)) {
            Success(aProductCode)
        } else {
            Failure(IllegalArgumentException("Product code doesn't exist"))
        }
    }

    return ProductCode.create(productCode)
            .let { bind(checkProduct, it) }
}

private fun getErrorFromListOfResults(results: List<Result<Any, Exception>>): Exception {
    results.filter {
        it is Failure
    }.first().let {
        return (it as? Failure)?.reason?:IllegalArgumentException("PlaceOrderImplementation: Unknown Reason")
    }
}