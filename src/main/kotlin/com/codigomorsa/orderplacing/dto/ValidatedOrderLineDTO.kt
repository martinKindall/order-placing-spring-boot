package com.codigomorsa.orderplacing.dto

import com.codigomorsa.orderplacing.implementation.ValidatedOrderLine
import com.codigomorsa.orderplacing.types.OrderQuantity
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class ValidatedOrderLineDTO(
        @Id val orderLine: Int,
        val productCode: String,
        val quantity: Float
) {
    companion object {
        fun toDto(order: ValidatedOrderLine): ValidatedOrderLineDTO {
            return ValidatedOrderLineDTO(
                    order.orderLineId.orderId,
                    order.productCode.toString(),
                    order.quantity.let {
                        when (it) {
                            is OrderQuantity.UnitQuantity -> it.quantity.toFloat()
                            is OrderQuantity.KilogramQuantity -> it.quantity
                        }
                    }
            )
        }
    }
}