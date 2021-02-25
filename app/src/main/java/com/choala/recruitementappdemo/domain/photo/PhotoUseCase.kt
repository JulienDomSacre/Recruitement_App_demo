package com.choala.recruitementappdemo.domain.photo

import com.choala.recruitementappdemo.domain.common.ResultState
import com.choala.recruitementappdemo.domain.repository.IRepository
import kotlinx.coroutines.flow.Flow

class PhotoUseCase(private val repo: IRepository) {

    fun getPhotos(albumId: Int): Flow<ResultState<List<PhotoModel>, PhotoError>> {
        return repo.getPhotos(albumId)
    }
}