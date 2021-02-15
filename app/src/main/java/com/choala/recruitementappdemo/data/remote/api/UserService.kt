package com.choala.recruitementappdemo.data.remote.api

import com.choala.recruitementappdemo.data.remote.response.UserResponse
import retrofit2.http.GET

interface UserService {
    @GET("users")
    suspend fun getUserList(): List<UserResponse>
}