package com.choala.recruitementappdemo.data.local.room.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.choala.recruitementappdemo.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert
    suspend fun insertAll(userList: List<UserEntity>)

    @Query("select * from user_table")
    fun userList(): Flow<List<UserEntity>>
}