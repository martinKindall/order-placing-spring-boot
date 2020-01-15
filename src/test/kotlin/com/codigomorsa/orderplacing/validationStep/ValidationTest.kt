package com.codigomorsa.orderplacing.validationStep

import com.codigomorsa.orderplacing.implementation.CheckProductCodeExists
import com.codigomorsa.orderplacing.implementation.toAddress
import com.codigomorsa.orderplacing.implementation.toCustomerInfo
import com.codigomorsa.orderplacing.implementation.toProductCode
import com.codigomorsa.orderplacing.types.*
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test


class ValidationTest {
    private lateinit var unvalidatedCustomerInfoCorrect: UnvalidatedCustomerInfo
    private lateinit var unvalidatedCustomerInfoInCorrectEmail: UnvalidatedCustomerInfo
    private lateinit var unvalidatedCustomerInfoInCorrectName: UnvalidatedCustomerInfo
    private lateinit var unvalidatedAddressCorrect: UnvalidatedAddress
    private lateinit var unvalidatedAddressTooLong: UnvalidatedAddress
    private lateinit var checkProductCodeExistsTrue: CheckProductCodeExists
    private lateinit var checkProductCodeExistsFalse: CheckProductCodeExists

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
        checkProductCodeExistsTrue = {true}
        checkProductCodeExistsFalse = {false}
    }

    @Test
    fun toCustomerTest() {
        val customerInfo = toCustomerInfo(unvalidatedCustomerInfoCorrect)
        assertTrue((customerInfo as? Success)?.value is CustomerInfo)

        val exception: Exception = (toCustomerInfo(unvalidatedCustomerInfoInCorrectEmail) as Failure).reason
        assertTrue(exception.message?.contains("Email address not valid.")?: false)

        val exception2: Exception = (toCustomerInfo(unvalidatedCustomerInfoInCorrectName) as Failure).reason
        assertTrue(exception2.message?.contains("String must be not greater than 50 characters.")?: false)
    }

    @Test
    fun toAddressTest() {
        val address = toAddress(unvalidatedAddressCorrect)
        assertTrue((address as? Success)?.value is Address)

        val exception: Exception = (toAddress(unvalidatedAddressTooLong) as Failure).reason
        assertTrue(exception.message?.contains("String must be not greater than 50 characters.")?: false)
    }

    @Test
    fun toProductCodeTest() {
        val productCode = "asd123"
        val validProductCode = toProductCode(checkProductCodeExistsTrue, productCode)
        (validProductCode as? Success)?.value!!.let {
            assertTrue(it.productCode.value == productCode)
        }

        val exception = (toProductCode(checkProductCodeExistsFalse, productCode) as Failure).reason
        assertTrue(exception.message?.contains("Product code doesn't exist")?: false)

        val productCodeTooLong = "sadfasdfasdfasdfasdfasdfadfasdfasdfasdfasdfasdfasdfasdf"
        val exception2 = (toProductCode(checkProductCodeExistsTrue, productCodeTooLong) as Failure).reason
        assertTrue(exception2.message?.contains("Product code not valid.")?: false)
    }
}
