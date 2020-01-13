package com.codigomorsa.orderplacing.types

data class OrderId(val orderId: Int)
data class CustomerId(val customerId: Int)
data class ProductCode(val productCode: String)

abstract class OrderQuantity {
    data class UnitQuantity(val quantity: Int): OrderQuantity()
    data class KilogramQuantity(val quantity: Float): OrderQuantity()
}