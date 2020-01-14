package com.codigomorsa.orderplacing.Utils

import com.codigomorsa.orderplacing.types.Failure
import com.codigomorsa.orderplacing.types.Result
import com.codigomorsa.orderplacing.types.Success

typealias SwitchFun<T, Y> = (x: T) -> Result<Y, Exception>

fun <T, Y>bind(aFun: SwitchFun<T, Y>, anArgument: Result<T, Exception>): Result<Y, Exception> {
    return when (anArgument) {
        is Success -> aFun(anArgument.value)
        is Failure -> anArgument
    }
}