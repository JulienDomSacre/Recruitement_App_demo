package com.choala.recruitementappdemo.data.remote.response

import com.google.gson.annotations.SerializedName

data class AlbumResponse(
    //use Serialized here because I had a bug with Gson
    // where it return default value (0), I don't know why
    @SerializedName("userId")
    val userId: Int,
    val id: Int,
    val title: String
)