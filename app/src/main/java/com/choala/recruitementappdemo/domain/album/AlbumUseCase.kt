package com.choala.recruitementappdemo.domain.album

import com.choala.recruitementappdemo.domain.common.ResultState
import com.choala.recruitementappdemo.domain.repository.IRepository
import kotlinx.coroutines.flow.Flow

class AlbumUseCase(private val repo: IRepository) {

    fun getAlbums(userId: Int): Flow<ResultState<List<AlbumModel>, AlbumError>> {
        return repo.getAlbums(userId)
    }
}