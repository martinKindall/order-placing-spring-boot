package com.codigomorsa.orderplacing.types

data class PersonalName(val firstName: String50, val lastName: String50)
data class CustomerInfo(val name: PersonalName, val email: EmailAddress)
data class Address(val addres: String50, val city: String50, val zipCode: String50)