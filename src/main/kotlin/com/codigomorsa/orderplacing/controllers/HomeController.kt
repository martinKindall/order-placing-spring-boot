package com.codigomorsa.orderplacing.controllers

import com.codigomorsa.orderplacing.implementation.ValidatedOrder
import com.codigomorsa.orderplacing.implementation.toValidatedOrder
import com.codigomorsa.orderplacing.services.OrderService
import com.codigomorsa.orderplacing.types.Failure
import com.codigomorsa.orderplacing.types.Result
import com.codigomorsa.orderplacing.types.Success
import com.codigomorsa.orderplacing.types.UnvalidatedOrder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@RestController
class HomeController(private val orderService: OrderService) {

    @GetMapping("/")
    fun home(): String {
        return "Hi there!"
    }

    @PostMapping("/order")
    fun create(
            @RequestBody order: Flux<UnvalidatedOrder>
    ): Mono<String> {
        return orderService.save(order).map {
            when (it) {
                is Success -> it.value
                is Failure -> "There was an error saving the order: " + it.reason.message
            }
        }
    }
}