package com.choala.recruitementappdemo.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User_Table")
data class UserEntity (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    @ColumnInfo(name = "user_name")
    val username: String,
    val email: String,
    val phone: String,
    val website: String
)