package com.choala.recruitementappdemo.domain.common

sealed class ResultState<out T : Any, out E : Any> {
    data class Success<out T : Any>(val data: T) : ResultState<T, Nothing>()
    data class Error<out E : Any>(val error: E) : ResultState<Nothing, E>()
}
