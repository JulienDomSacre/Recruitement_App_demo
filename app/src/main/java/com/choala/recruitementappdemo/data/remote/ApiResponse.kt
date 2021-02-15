package com.choala.recruitementappdemo.data.remote

sealed class ApiResponse<out R> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val error: NetworkError) : ApiResponse<Nothing>()
}

sealed class NetworkError{
    object NoNetworkError: NetworkError()
    object TimeOutError: NetworkError()
    object EmptyResource: NetworkError()
    data class UnknownError(val message: String): NetworkError()
}