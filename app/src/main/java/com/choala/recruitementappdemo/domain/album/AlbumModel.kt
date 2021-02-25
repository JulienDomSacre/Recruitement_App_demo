package com.choala.recruitementappdemo.domain.album

data class AlbumModel(
    val id: Int,
    val userId: Int,
    val title: String
)

sealed class AlbumError {
    object NoNetworkError : AlbumError()
    object TimeOutError : AlbumError()
    object EmptyResource : AlbumError()
    data class UnknownError(val message: String) : AlbumError()
}