package com.codigomorsa.orderplacing.types

import java.lang.IllegalArgumentException

sealed class Result<out Success, out Failure>

data class Success<out Success>(val value: Success) : Result<Success, Nothing>()
data class Failure<out Failure>(val reason: Failure) : Result<Nothing, Failure>()


class String50 private constructor(val value: String) {
    companion object {
        fun create(value: String): Result<String50, Exception> {
            return if (value.length <= 50) {
                Success(String50(value))
            } else {
                Failure(IllegalArgumentException("String must be not greater than 50 characters."))
            }
        }
    }
}

class EmailAddress private constructor(val emailAddress: String) {
    companion object {
        fun create(emailAddress: String): Result<EmailAddress, Exception> {
            val regexPattern = ".+@.+".toRegex()
            return if (regexPattern.containsMatchIn(emailAddress)) {
                Success(EmailAddress(emailAddress))
            } else {
                Failure(IllegalArgumentException("Email address not valid."))
            }
        }
    }
}

data class OrderId(val orderId: Int)
data class CustomerId(val customerId: Int)
data class ProductCode(private val productCode: String50)
data class ShippingAddress(val productCode: String50)
data class ZipCode(val zipCode: String)
data class Price(val price: Float)
class BillingAmout private constructor(val amount: Float)

sealed class OrderQuantity {
    data class UnitQuantity(val quantity: Int): OrderQuantity()
    data class KilogramQuantity(val quantity: Float): OrderQuantity()
}

