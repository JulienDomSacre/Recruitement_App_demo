package com.choala.recruitementappdemo.data.remote.api

import com.choala.recruitementappdemo.data.remote.response.AlbumResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AlbumService {
    @GET("users/{userId}/albums")
    suspend fun getAlbumList(@Path("userId") id: Int): List<AlbumResponse>

}