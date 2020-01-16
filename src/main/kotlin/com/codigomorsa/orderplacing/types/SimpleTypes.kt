package com.codigomorsa.orderplacing.types

import java.util.concurrent.CompletableFuture
import kotlin.IllegalArgumentException

sealed class Result<out Success, out Failure>

data class Success<out Success>(val value: Success) : Result<Success, Nothing>()
data class Failure<out Failure>(val reason: Failure) : Result<Nothing, Failure>()

typealias CompletableResult<Success, Failure> = CompletableFuture<Result<Success, Failure>>

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

class ProductCode private constructor(val productCode: String50) {
    companion object {
        fun create(code: String): Result<ProductCode, Exception> {
            val productCode = String50.create(code)
            return if (productCode is Success) {
                Success(ProductCode(productCode.value))
            } else {
                Failure(IllegalArgumentException("Product code not valid."))
            }
        }
    }
}

data class OrderId(val orderId: Int)
data class OrderLineId(val orderId: Int)

sealed class OrderQuantity {
    data class UnitQuantity(val quantity: Int): OrderQuantity()
    data class KilogramQuantity(val quantity: Float): OrderQuantity()

    companion object {
        fun create(number: Any): OrderQuantity {
            return when(number) {
                is Int -> UnitQuantity(number)
                is Float -> KilogramQuantity(number)
                else -> throw IllegalArgumentException("Invalid quantity type")
            }
        }
    }
}

