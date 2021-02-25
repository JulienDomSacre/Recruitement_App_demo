package com.choala.recruitementappdemo.domain.repository

import com.choala.recruitementappdemo.domain.album.AlbumError
import com.choala.recruitementappdemo.domain.album.AlbumModel
import com.choala.recruitementappdemo.domain.common.ResultState
import com.choala.recruitementappdemo.domain.photo.PhotoError
import com.choala.recruitementappdemo.domain.photo.PhotoModel
import com.choala.recruitementappdemo.domain.user.UserError
import com.choala.recruitementappdemo.domain.user.UserModel
import kotlinx.coroutines.flow.Flow

interface IRepository {
    fun getUsers(): Flow<ResultState<List<UserModel>, UserError>>
    fun getAlbums(userId: Int): Flow<ResultState<List<AlbumModel>, AlbumError>>
    fun getPhotos(albumId: Int): Flow<ResultState<List<PhotoModel>, PhotoError>>
}