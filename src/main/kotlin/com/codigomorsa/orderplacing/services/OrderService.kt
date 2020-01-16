package com.codigomorsa.orderplacing.services

import com.codigomorsa.orderplacing.Utils.listOfResultsToResult
import com.codigomorsa.orderplacing.implementation.ValidatedOrder
import com.codigomorsa.orderplacing.implementation.toValidatedOrder
import com.codigomorsa.orderplacing.types.Failure
import com.codigomorsa.orderplacing.types.Result
import com.codigomorsa.orderplacing.types.Success
import com.codigomorsa.orderplacing.types.UnvalidatedOrder
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class OrderService {
    private val checkProductCodeExists = {something: Any -> true}

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
                            is Success -> {
                                println("Saving to DB")
                                Mono.just(Success("Order successfully saved!"))
                            }
                            is Failure -> Mono.just(totalResult)
                        }
                    }
                }
    }
}