package com.choala.recruitementappdemo.data.remote.response

import com.google.gson.annotations.SerializedName

data class PhotoResponse(
    @SerializedName("albumId")
    val albumId: Int,
    val id: Int,
    val title: String,
    val url: String,
    @SerializedName("thumbnailUrl")
    val thumbnailUrl: String
)