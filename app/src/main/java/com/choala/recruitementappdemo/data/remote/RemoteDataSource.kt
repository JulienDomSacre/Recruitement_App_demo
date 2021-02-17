package com.choala.recruitementappdemo.data.remote

import com.choala.recruitementappdemo.data.remote.api.AlbumService
import com.choala.recruitementappdemo.data.remote.api.PhotoService
import com.choala.recruitementappdemo.data.remote.api.UserService
import com.choala.recruitementappdemo.data.remote.response.AlbumResponse
import com.choala.recruitementappdemo.data.remote.response.PhotoResponse
import com.choala.recruitementappdemo.data.remote.response.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import java.net.SocketTimeoutException

class RemoteDataSource(
    private val userService: UserService,
    private val albumService: AlbumService,
    private val photoService: PhotoService
) {

    fun getUserList(): Flow<ApiResponse<List<UserResponse>>> {
        return flow {
            try {
                val response = userService.getUserList()
                if (response.isNotEmpty())
                    emit(ApiResponse.Success(response))
                else
                    emit(ApiResponse.Error(NetworkError.EmptyResource))
            } catch (e: Exception) {
                emit(ApiResponse.Error(mapException(e)))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getAlbumList(userId: Int): Flow<ApiResponse<List<AlbumResponse>>> {
        return flow {
            try {
                val response = albumService.getAlbumList(userId)
                if (response.isNotEmpty())
                    emit(ApiResponse.Success(response))
                else
                    emit(ApiResponse.Error(NetworkError.EmptyResource))
            } catch (e: Exception) {
                emit(ApiResponse.Error(mapException(e)))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getPhotoList(albumId: Int): Flow<ApiResponse<List<PhotoResponse>>> {
        return flow {
            try {
                val response = photoService.getPhotoList(albumId)
                if (response.isNotEmpty())
                    emit(ApiResponse.Success(response))
                else
                    emit(ApiResponse.Error(NetworkError.EmptyResource))
            } catch (e: Exception) {
                emit(ApiResponse.Error(mapException(e)))
            }
        }.flowOn(Dispatchers.IO)
    }

    private fun mapException(exception: Exception): NetworkError {
        return when (exception) {
            is SocketTimeoutException -> NetworkError.TimeOutError
            is IOException -> NetworkError.NoNetworkError
            else -> NetworkError.UnknownError(exception.message ?: "Unknown error")
        }
    }
}