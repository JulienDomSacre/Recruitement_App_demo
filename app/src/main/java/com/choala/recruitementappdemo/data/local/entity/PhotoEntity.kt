package com.choala.recruitementappdemo.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Photo_Table")
data class PhotoEntity (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @ColumnInfo(name ="album_id")
    val albumId: Int,
    val title: String,
    val url: String,
    @ColumnInfo(name ="thumbnail_url")
    val thumbnailUrl: String
)