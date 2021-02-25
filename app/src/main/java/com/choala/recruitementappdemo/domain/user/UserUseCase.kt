package com.choala.recruitementappdemo.domain.user

import com.choala.recruitementappdemo.domain.common.ResultState
import com.choala.recruitementappdemo.domain.repository.IRepository
import kotlinx.coroutines.flow.Flow

class UserUseCase(private val repo: IRepository) {

    fun getUsers(): Flow<ResultState<List<UserModel>, UserError>> {
        return repo.getUsers()
    }
}