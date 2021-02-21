package com.choala.recruitementappdemo.domain.user

data class UserModel(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val phone: String,
    val website: String
)

sealed class UserError {
    object NoNetworkError : UserError()
    object TimeOutError : UserError()
    object EmptyResource : UserError()
    data class UnknownError(val message: String) : UserError()
}
