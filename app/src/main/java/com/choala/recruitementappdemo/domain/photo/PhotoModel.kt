package com.choala.recruitementappdemo.domain.photo

data class PhotoModel(
    val albumId: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)

sealed class PhotoError {
    object NoNetworkError : PhotoError()
    object TimeOutError : PhotoError()
    object EmptyResource : PhotoError()
    data class UnknownError(val message: String) : PhotoError()
}