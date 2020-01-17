package com.codigomorsa.orderplacing.repositories

import com.codigomorsa.orderplacing.dto.ValidatedOrderLineDTO
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface OrderLineRepository: ReactiveCrudRepository<ValidatedOrderLineDTO, String> {
}