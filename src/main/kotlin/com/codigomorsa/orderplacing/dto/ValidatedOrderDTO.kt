package com.codigomorsa.orderplacing.dto

import com.codigomorsa.orderplacing.implementation.ValidatedOrder
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class ValidatedOrderDTO(
        @Id val orderId: Int,
        val customerInfo: CustomerInfoDTO,
        val address: AddressDTO,
        val lines: List<Int>
) {
    companion object {
        fun toDto(order: ValidatedOrder): ValidatedOrderDTO {
            return ValidatedOrderDTO(
                    order.orderId.orderId,
                    CustomerInfoDTO.toDTO(order.customerInfo),
                    AddressDTO.toDTO(order.shippingAddress),
                    order.lines.map {
                        it.orderLineId.orderId
                    }
            )
        }
    }
}