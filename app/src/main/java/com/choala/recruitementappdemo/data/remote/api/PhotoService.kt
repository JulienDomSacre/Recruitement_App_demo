package com.choala.recruitementappdemo.data.remote.api

import com.choala.recruitementappdemo.data.remote.response.PhotoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PhotoService {
    @GET("users/1/photos")
    suspend fun getPhotoList(@Query("albumId") id: Int): List<PhotoResponse>
}