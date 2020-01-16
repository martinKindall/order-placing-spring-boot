package com.codigomorsa.orderplacing.services

import com.codigomorsa.orderplacing.Utils.listOfResultsToResult
import com.codigomorsa.orderplacing.dto.ValidatedOrderDTO
import com.codigomorsa.orderplacing.implementation.ValidatedOrder
import com.codigomorsa.orderplacing.implementation.toValidatedOrder
import com.codigomorsa.orderplacing.repositories.OrderRepository
import com.codigomorsa.orderplacing.types.Failure
import com.codigomorsa.orderplacing.types.Result
import com.codigomorsa.orderplacing.types.Success
import com.codigomorsa.orderplacing.types.UnvalidatedOrder
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class OrderService(val orderRepository: OrderRepository) {
    private val checkProductCodeExists = {something: Any -> true}

    fun findAll(): Flux<ValidatedOrderDTO> {
        return orderRepository.findAll()
    }

    fun save(orders: Flux<UnvalidatedOrder>): Mono<Result<String, Exception>> {
        return orders.map {
            toValidatedOrder(checkProductCodeExists, it)
        }.reduce(listOf(),
                {
                    acc: List<Result<ValidatedOrder, Exception>>,
                    result: Result<ValidatedOrder, Exception> ->
                    acc + listOf(result)
                })
                .flatMap {
                    listOfResultsToResult(it).let { totalResult ->
                        when (totalResult) {
                            is Success -> saveOrders(totalResult.value).map {
                                Success("Orders successfully saved!")
                            }
                            is Failure -> Mono.just(totalResult)
                        }
                    }
                }
    }

    private fun saveOrders(orders: List<ValidatedOrder>): Mono<Unit> {
        return Flux.fromIterable(orders.map {
            ValidatedOrderDTO.toDto(it)
        }).flatMap {
            orderRepository.save(it)
        }.then(Mono.just(Unit))
    }
}