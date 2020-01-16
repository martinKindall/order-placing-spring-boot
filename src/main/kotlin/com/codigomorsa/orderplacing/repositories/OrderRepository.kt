package com.codigomorsa.orderplacing.repositories

import com.codigomorsa.orderplacing.dto.ValidatedOrderDTO
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface OrderRepository: ReactiveCrudRepository<ValidatedOrderDTO, String> {

}