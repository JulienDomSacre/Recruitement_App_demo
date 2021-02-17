package com.choala.recruitementappdemo.data

import com.choala.recruitementappdemo.data.local.mapper.LocalMapper
import com.choala.recruitementappdemo.data.local.room.album.AlbumDao
import com.choala.recruitementappdemo.data.local.room.photo.PhotoDao
import com.choala.recruitementappdemo.data.local.room.user.UserDao
import com.choala.recruitementappdemo.data.remote.ApiResponse
import com.choala.recruitementappdemo.data.remote.NetworkError
import com.choala.recruitementappdemo.data.remote.RemoteDataSource
import com.choala.recruitementappdemo.data.remote.mapper.RemoteMapper
import com.choala.recruitementappdemo.domain.album.AlbumError
import com.choala.recruitementappdemo.domain.album.AlbumModel
import com.choala.recruitementappdemo.domain.common.ResultState
import com.choala.recruitementappdemo.domain.photo.PhotoError
import com.choala.recruitementappdemo.domain.photo.PhotoModel
import com.choala.recruitementappdemo.domain.repository.IRepository
import com.choala.recruitementappdemo.domain.user.UserError
import com.choala.recruitementappdemo.domain.user.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class Repository(
    private val remoteDataSource: RemoteDataSource,
    private val localAlbumDao: AlbumDao,
    private val localPhotoDao: PhotoDao,
    private val localUserDao: UserDao,
    private val mapToEntity: RemoteMapper,
    private val mapToModel: LocalMapper
) : IRepository {

    override fun getUsers(): Flow<ResultState<List<UserModel>, UserError>> =
        flow {
            localUserDao.userList().collect { localUsers ->
                if (localUsers.isEmpty()) {
                    remoteDataSource.getUserList().collect {
                        when (it) {
                            is ApiResponse.Error -> emit(mapErrorUser(it.error))
                            is ApiResponse.Success -> localUserDao.insertAll(it.data.map { userResponse ->
                                mapToEntity.mapToUserEntity(
                                    userResponse
                                )
                            })
                        }
                    }
                } else {
                    emit(
                        ResultState.Success(localUsers.map { userEntity ->
                            mapToModel.mapToUserModel(
                                userEntity
                            )
                        })
                    )
                }
            }

        }

    private fun mapErrorUser(error: NetworkError): ResultState.Error<UserError> {
        return when (error) {
            NetworkError.EmptyResource -> ResultState.Error(UserError.EmptyResource)
            NetworkError.NoNetworkError -> ResultState.Error(UserError.NoNetworkError)
            NetworkError.TimeOutError -> ResultState.Error(UserError.TimeOutError)
            is NetworkError.UnknownError -> ResultState.Error(UserError.UnknownError(error.message))
        }

    }

    override fun getAlbums(userId: Int): Flow<ResultState<List<AlbumModel>, AlbumError>> =
        flow {
            localAlbumDao.getAlbumListByUserId(userId).collect { localAlbums ->
                if (localAlbums.isEmpty()) {
                    remoteDataSource.getAlbumList(userId).collect {
                        when (it) {
                            is ApiResponse.Error -> emit(mapErrorAlbum(it.error))
                            is ApiResponse.Success -> localAlbumDao.insertAll(it.data.map { albumResponse ->
                                mapToEntity.mapToAlbumEntity(
                                    albumResponse
                                )
                            })
                        }
                    }
                } else {
                    emit(
                        ResultState.Success(localAlbums.map { albumEntity ->
                            mapToModel.mapToAlbumModel(
                                albumEntity
                            )
                        })
                    )
                }
            }
        }

    private fun mapErrorAlbum(error: NetworkError): ResultState.Error<AlbumError> {
        return when (error) {
            NetworkError.EmptyResource -> ResultState.Error(AlbumError.EmptyResource)
            NetworkError.NoNetworkError -> ResultState.Error(AlbumError.NoNetworkError)
            NetworkError.TimeOutError -> ResultState.Error(AlbumError.TimeOutError)
            is NetworkError.UnknownError -> ResultState.Error(AlbumError.UnknownError(error.message))
        }

    }

    override fun getPhotos(albumId: Int): Flow<ResultState<List<PhotoModel>, PhotoError>> =
        flow {
            localPhotoDao.getPhotoListByAlbumId(albumId).collect { localPhotos ->
                if (localPhotos.isEmpty()) {
                    remoteDataSource.getPhotoList(albumId).collect {
                        when (it) {
                            is ApiResponse.Error -> emit(mapErrorPhoto(it.error))
                            is ApiResponse.Success -> localPhotoDao.insertAll(it.data.map { photoResponse ->
                                mapToEntity.mapToPhotoEntity(
                                    photoResponse
                                )
                            })
                        }
                    }
                } else {
                    emit(
                        ResultState.Success(localPhotos.map { photoEntity ->
                            mapToModel.mapToPhotoModel(
                                photoEntity
                            )
                        })
                    )
                }
            }
        }

    private fun mapErrorPhoto(error: NetworkError): ResultState.Error<PhotoError> {
        return when (error) {
            NetworkError.EmptyResource -> ResultState.Error(PhotoError.EmptyResource)
            NetworkError.NoNetworkError -> ResultState.Error(PhotoError.NoNetworkError)
            NetworkError.TimeOutError -> ResultState.Error(PhotoError.TimeOutError)
            is NetworkError.UnknownError -> ResultState.Error(PhotoError.UnknownError(error.message))
        }
    }
}