package com.codigomorsa.orderplacing.types

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
        val quantity: OrderQuantity
)

data class UnvalidatedOrder(
        val orderId: String,
        val customerInfo: UnvalidatedCustomerInfo,
        val shippingAddress: UnvalidatedAddress,
        val unvalidOrderLines: List<UnvalidatedOrderLine>
)

interface PlaceOrder {
    fun place(order: UnvalidatedOrder): CompletableResult<UnvalidatedOrder, Exception>   // todo: define event output
}