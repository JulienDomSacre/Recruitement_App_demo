package com.choala.recruitementappdemo.data.remote.response

data class UserResponse(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val address: Address,
    val phone: String,
    val website: String,
    val company: Company
)

data class Address(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    val geo: Geo
)

data class Company(
    val name: String,
    val catchPhrase: String,
    val bs: String
)

data class Geo(
    val lat: String,
    val lng: String
)