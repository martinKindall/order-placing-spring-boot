package com.codigomorsa.orderplacing.validationStep

import com.codigomorsa.orderplacing.implementation.toAddress
import com.codigomorsa.orderplacing.implementation.toCustomerInfo
import com.codigomorsa.orderplacing.types.UnvalidatedAddress
import com.codigomorsa.orderplacing.types.UnvalidatedCustomerInfo
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException


class ValidationTest {
    private lateinit var unvalidatedCustomerInfoCorrect: UnvalidatedCustomerInfo
    private lateinit var unvalidatedCustomerInfoInCorrectEmail: UnvalidatedCustomerInfo
    private lateinit var unvalidatedCustomerInfoInCorrectName: UnvalidatedCustomerInfo
    private lateinit var unvalidatedAddressCorrect: UnvalidatedAddress
    private lateinit var unvalidatedAddressTooLong: UnvalidatedAddress

    init {
        unvalidatedCustomerInfoCorrect = UnvalidatedCustomerInfo(
                "jane",
                "doe",
                "hi@gmail.com"
        )
        unvalidatedCustomerInfoInCorrectEmail = UnvalidatedCustomerInfo(
                "jane",
                "doe",
                "higmail"
        )
        unvalidatedCustomerInfoInCorrectName = UnvalidatedCustomerInfo(
                "janeadfadsdfasdfasdfasdfadfasfasjaneadfadsdfasdfasdfasdfadfasfas",
                "doe",
                "hi@gmail.com"
        )
        unvalidatedAddressCorrect = UnvalidatedAddress(
                "Blanco Encalada xxxx",
                "Santiago",
                "8320000"
        )
        unvalidatedAddressTooLong = UnvalidatedAddress(
                "Blanco Encalada xxxx",
                "Santiago",
                "janeadfadsdfasdfasdfasdfadfasfasjaneadfadsdfasdfasdfasdfadfasfas"
        )
    }

    @Test
    fun toCustomerTest() {
        val customerInfo = toCustomerInfo(unvalidatedCustomerInfoCorrect)
        val exception: IllegalArgumentException = assertThrows {
            val customerInfoError = toCustomerInfo(unvalidatedCustomerInfoInCorrectEmail)
        }
        assertTrue(exception.message?.contains("Email address not valid.")?: false)

        val exception2: IllegalArgumentException = assertThrows {
            val customerInfoError = toCustomerInfo(unvalidatedCustomerInfoInCorrectName)
        }
        assertTrue(exception2.message?.contains("String must be not greater than 50 characters.")?: false)
    }

    @Test
    fun toAddressTest() {
        val address = toAddress(unvalidatedAddressCorrect)
        val exception: IllegalArgumentException = assertThrows {
            val addressError = toAddress(unvalidatedAddressTooLong)
        }
        assertTrue(exception.message?.contains("String must be not greater than 50 characters.")?: false)
    }
}
