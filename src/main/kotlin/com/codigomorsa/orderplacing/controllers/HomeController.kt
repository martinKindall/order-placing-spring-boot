package com.codigomorsa.orderplacing.controllers

import com.codigomorsa.orderplacing.implementation.ValidatedOrder
import com.codigomorsa.orderplacing.implementation.toValidatedOrder
import com.codigomorsa.orderplacing.types.Failure
import com.codigomorsa.orderplacing.types.ProductCode
import com.codigomorsa.orderplacing.types.Result
import com.codigomorsa.orderplacing.types.Success
import com.codigomorsa.orderplacing.types.UnvalidatedOrder
import kotlinx.coroutines.reactive.collect
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@RestController
class HomeController {

    @GetMapping("/")
    fun home(): String {
        return "Hi there!"
    }

    @PostMapping("/order")
    fun create(
            @RequestBody order: Flux<UnvalidatedOrder>
    ): Mono<List<ValidatedOrder>> {
        val checkProductCodeExists = {code: Any -> true}

        return order.map {
            toValidatedOrder(checkProductCodeExists, it)
        }.reduce(
                listOf(),
                {
                    acc: List<ValidatedOrder>,
                    res: Result<ValidatedOrder, Exception> ->
            if (res is Success) {
                acc + listOf(res.value)
            } else {
                throw IllegalArgumentException("Not a valid order.")
            }
        })
    }
}