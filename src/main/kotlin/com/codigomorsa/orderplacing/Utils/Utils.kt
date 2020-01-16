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

fun <T>listOfResultsToResult(results: List<Result<T, Exception>>): Result<List<T>, Exception> {
    val initialVal = Success(listOf<T>())

    return results.fold(initialVal, {acc: Result<List<T>, Exception>, result ->
        prepend(result, acc)
    })
}

private fun <T>prepend(
        firstR: Result<T, Exception>,
        restR: Result<List<T>, Exception>): Result<List<T>, Exception> {

    return if (firstR is Success && restR is Success) {
        val newList = restR.value + listOf(firstR.value)
        Success(newList)
    } else if (firstR is Failure) {
        firstR
    } else {
        restR
    }
}