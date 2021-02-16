package com.choala.recruitementappdemo.data.local.room.album

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.choala.recruitementappdemo.data.local.entity.AlbumEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDao {

    @Insert
    suspend fun insertAll(albumsList: List<AlbumEntity>)

    @Query("select * from Album_Table")
    fun albumList(): Flow<List<AlbumEntity>>

    @Query("select * from Album_Table where user_id= :id")
    fun getAlbumListByUserId(id: Int): Flow<List<AlbumEntity>>
}