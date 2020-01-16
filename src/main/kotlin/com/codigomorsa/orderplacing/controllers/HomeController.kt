package com.codigomorsa.orderplacing.controllers

import com.codigomorsa.orderplacing.types.UnvalidatedAddress
import com.codigomorsa.orderplacing.types.UnvalidatedOrder
import org.springframework.stereotype.Controller
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
            @RequestBody order: Flux<UnvalidatedAddress>
    ): Mono<String> {
        return order.map {
            println(it.toString())
        }.then(Mono.just("Hello there"))
    }
}