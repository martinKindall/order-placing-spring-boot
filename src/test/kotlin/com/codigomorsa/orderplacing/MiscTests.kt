package com.codigomorsa.orderplacing

import com.codigomorsa.orderplacing.Utils.bind
import com.codigomorsa.orderplacing.types.Failure
import org.junit.jupiter.api.Test
import com.codigomorsa.orderplacing.types.Result
import com.codigomorsa.orderplacing.types.Success
import org.junit.jupiter.api.Assertions.assertTrue

class MiscTests {

    inner class Apple()
    inner class Banana()
    inner class PineApple()
    inner class Cherry()

    @Test
    fun bindTest() {
        val appleToBanana  = { apple: Apple ->
            Success(Banana())
        }
        val bananaToPineApple = { banana: Banana ->
            Success(PineApple())
        }
        val pineAppleToCherry = { pineApple: PineApple ->
            if (true) Success(Cherry()) else Failure(IllegalArgumentException(""))
        }

        val anApple = Apple()
        val cherryResult =
                appleToBanana(anApple).let {
                    bind(bananaToPineApple, it)
                }.let {
                    bind(pineAppleToCherry, it)
                }

        assertTrue((cherryResult as? Success)?.value is Cherry)
    }
}