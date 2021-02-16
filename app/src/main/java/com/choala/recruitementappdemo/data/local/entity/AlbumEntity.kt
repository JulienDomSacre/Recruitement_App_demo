package com.choala.recruitementappdemo.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Album_Table")
data class AlbumEntity (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name ="id")
    val id: Int,
    @ColumnInfo(name ="user_id")
    val userId: Int,
    val title: String
)