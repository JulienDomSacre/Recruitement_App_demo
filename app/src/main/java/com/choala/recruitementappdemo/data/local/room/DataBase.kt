package com.choala.recruitementappdemo.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.choala.recruitementappdemo.data.local.entity.AlbumEntity
import com.choala.recruitementappdemo.data.local.entity.PhotoEntity
import com.choala.recruitementappdemo.data.local.entity.UserEntity
import com.choala.recruitementappdemo.data.local.room.album.AlbumDao
import com.choala.recruitementappdemo.data.local.room.photo.PhotoDao
import com.choala.recruitementappdemo.data.local.room.user.UserDao

@Database(
    entities = [
        AlbumEntity::class,
        PhotoEntity::class,
        UserEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class DataBase : RoomDatabase() {
    abstract fun albumDao(): AlbumDao
    abstract fun photoDao(): PhotoDao
    abstract fun userDao(): UserDao
}