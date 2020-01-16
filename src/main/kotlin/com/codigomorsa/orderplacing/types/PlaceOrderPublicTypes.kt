package com.codigomorsa.orderplacing.types

import com.codigomorsa.orderplacing.implementation.ValidatedOrder

data class UnvalidatedCustomerInfo(
        val firstName: String,
        val lastName: String,
        val email: String
)

data class UnvalidatedAddress(
        val address: String,
        val city: String,
        val zipCode: String
)

data class UnvalidatedOrderLine(
        val orderLineId: String,
        val productCode: String,
        val quantity: Float
)

data class UnvalidatedOrder(
        val orderId: String,
        val customerInfo: UnvalidatedCustomerInfo,
        val shippingAddress: UnvalidatedAddress,
        val unvalidOrderLines: List<UnvalidatedOrderLine>
)

typealias PlaceOrder = (order: UnvalidatedOrder) -> CompletableResult<ValidatedOrder, Exception>