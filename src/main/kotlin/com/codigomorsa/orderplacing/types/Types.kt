package com.codigomorsa.orderplacing.types

sealed class Result<out Success, out Failure>

data class Success<out Success>(val value: Success) : Result<Success, Nothing>()
data class Failure<out Failure>(val reason: Failure) : Result<Nothing, Failure>()


class String50 private constructor(val value: String) {
    companion object {
        fun create(value: String): Result<String50, Exception> {
            return if (value.length <= 50) {
                Success(String50(value))
            } else {
                Failure(RuntimeException("String must be not greater than 50 characters."))
            }
        }
    }
}

data class OrderId(val orderId: Int)
data class CustomerId(val customerId: Int)
data class ProductCode(private val productCode: String50)
data class ShippingAddress(val productCode: String50)

sealed class OrderQuantity {
    data class UnitQuantity(val quantity: Int): OrderQuantity()
    data class KilogramQuantity(val quantity: Float): OrderQuantity()
}

