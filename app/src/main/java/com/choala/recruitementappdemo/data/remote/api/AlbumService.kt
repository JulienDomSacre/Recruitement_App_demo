package com.choala.recruitementappdemo.data.remote.api

import com.choala.recruitementappdemo.data.remote.response.AlbumResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AlbumService {
    @GET("users/1/albums")
    suspend fun getAlbumList(@Query("userID") id: Int): List<AlbumResponse>

}