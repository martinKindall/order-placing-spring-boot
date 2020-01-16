package com.codigomorsa.orderplacing.implementation

import com.codigomorsa.orderplacing.Utils.bind
import com.codigomorsa.orderplacing.Utils.listOfResultsToResult
import com.codigomorsa.orderplacing.types.*
import kotlin.IllegalArgumentException
import com.codigomorsa.orderplacing.types.Result
import kotlin.Exception

typealias CheckProductCodeExists = (x: ProductCode) -> Boolean

data class ValidatedOrder(
        val orderId: OrderId,
        val customerInfo: CustomerInfo,
        val shippingAddress: Address,
        val lines: List<ValidatedOrderLine>
)

data class ValidatedOrderLine(
        val orderLineId: OrderLineId,
        val productCode: ProductCode,
        val quantity: OrderQuantity)

fun toCustomerInfo(
        unvalidatedCustomerInfo: UnvalidatedCustomerInfo): Result<CustomerInfo, Exception> {
    val firstName = String50.create(unvalidatedCustomerInfo.firstName)
    val lastName = String50.create(unvalidatedCustomerInfo.lastName)
    val email = EmailAddress.create(unvalidatedCustomerInfo.email)

    if (firstName is Success && lastName is Success && email is Success) {
        return Success(CustomerInfo(
                PersonalName(firstName.value, lastName.value),
                email.value
        ))
    } else {
        return Failure(getErrorFromListOfResults(listOf(firstName, lastName, email)))
    }
}

fun toAddress(unvalidatedAddress: UnvalidatedAddress): Result<Address, Exception> {
    val address = String50.create(unvalidatedAddress.address)
    val city = String50.create(unvalidatedAddress.city)
    val zipCode = String50.create(unvalidatedAddress.zipCode)

    if (address is Success && city is Success && zipCode is Success ) {
        return Success(Address(
                address.value,
                city.value,
                zipCode.value
        ))
    } else {
        return Failure(getErrorFromListOfResults(listOf(address, city, zipCode)))
    }
}

fun toProductCode(
        checkProductCodeExists: CheckProductCodeExists,
        productCode: String): Result<ProductCode, Exception> {
    val checkProduct = { aProductCode: ProductCode ->
        if (checkProductCodeExists(aProductCode)) {
            Success(aProductCode)
        } else {
            Failure(IllegalArgumentException("Product code doesn't exist"))
        }
    }

    return ProductCode.create(productCode)
            .let { bind(checkProduct, it) }
}

fun toValidatedOrderLine(
        checkProductCodeExists: CheckProductCodeExists,
        unvalidatedOrderLine: UnvalidatedOrderLine): Result<ValidatedOrderLine, Exception> {
    val validatedOrderLineAux = { product: ProductCode ->
        Success(ValidatedOrderLine(
                OrderLineId(unvalidatedOrderLine.orderLineId.toInt()),
                product,
                unvalidatedOrderLine.quantity
        ))
    }

    return bind(validatedOrderLineAux, toProductCode(checkProductCodeExists, unvalidatedOrderLine.productCode))
}

fun toValidatedOrder(
        checkProductCodeExists: CheckProductCodeExists,
        unvalidatedOrder: UnvalidatedOrder
): Result<ValidatedOrder, Exception> {
    val orderId = OrderId(unvalidatedOrder.orderId.toInt())
    val customerInfo = toCustomerInfo(unvalidatedOrder.customerInfo)
    val address = toAddress(unvalidatedOrder.shippingAddress)
    val orderLines = listOfResultsToResult(unvalidatedOrder.unvalidOrderLines.map {
        toValidatedOrderLine(checkProductCodeExists, it)
    })

    return if (customerInfo is Success && address is Success && orderLines is Success) {
        Success(ValidatedOrder(
                orderId,
                customerInfo.value,
                address.value,
                orderLines.value
        ))
    } else {
        Failure(getErrorFromListOfResults(listOf(customerInfo, address, orderLines)))
    }
}

private fun getErrorFromListOfResults(results: List<Result<Any, Exception>>): Exception {
    results.filter {
        it is Failure
    }.first().let {
        return (it as? Failure)?.reason?:IllegalArgumentException("PlaceOrderImplementation: Unknown Reason")
    }
}