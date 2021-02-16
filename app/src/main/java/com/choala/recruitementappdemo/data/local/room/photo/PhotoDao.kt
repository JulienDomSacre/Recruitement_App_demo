package com.choala.recruitementappdemo.data.local.room.photo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.choala.recruitementappdemo.data.local.entity.PhotoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {
    @Insert
    suspend fun insertAll(photoList: List<PhotoEntity>)

    @Query("select * from Photo_Table")
    fun photoList(): Flow<List<PhotoEntity>>

    @Query("select * from Photo_Table where album_id= :id")
    fun getPhotoListByAlbumId(id: Int): Flow<List<PhotoEntity>>
}