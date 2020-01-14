package com.codigomorsa.orderplacing.validationStep

import com.codigomorsa.orderplacing.implementation.toCustomerInfo
import com.codigomorsa.orderplacing.types.UnvalidatedCustomerInfo
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException


class ValidationTest {
    private lateinit var unvalidatedCustomerInfoCorrect: UnvalidatedCustomerInfo
    private lateinit var unvalidatedCustomerInfoInCorrectEmail: UnvalidatedCustomerInfo
    private lateinit var unvalidatedCustomerInfoInCorrectName: UnvalidatedCustomerInfo


    @Test
    fun toCustomerTest() {
        unvalidatedCustomerInfoCorrect = UnvalidatedCustomerInfo(
                "jane",
                "doe",
                "hi@gmail.com"
        )

        val customerInfo = toCustomerInfo(unvalidatedCustomerInfoCorrect)

        unvalidatedCustomerInfoInCorrectEmail = UnvalidatedCustomerInfo(
                "jane",
                "doe",
                "higmail"
        )

        val exception: IllegalArgumentException = assertThrows {
            val customerInfoError = toCustomerInfo(unvalidatedCustomerInfoInCorrectEmail)
        }

        assertTrue(exception.message?.contains("Email address not valid.")?: false)

        unvalidatedCustomerInfoInCorrectName = UnvalidatedCustomerInfo(
                "janeadfadsdfasdfasdfasdfadfasfasjaneadfadsdfasdfasdfasdfadfasfas",
                "doe",
                "hi@gmail.com"
        )

        val exception2: IllegalArgumentException = assertThrows {
            val customerInfoError = toCustomerInfo(unvalidatedCustomerInfoInCorrectName)
        }

        assertTrue(exception2.message?.contains("String must be not greater than 50 characters.")?: false)
    }
}
