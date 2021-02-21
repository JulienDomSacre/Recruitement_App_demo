package com.choala.recruitementappdemo.ui.di

import com.choala.recruitementappdemo.ui.userList.ListUserViewModel
import com.choala.recruitementappdemo.ui.userList.mapper.ListUserUiDataMapper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { ListUserViewModel(userUseCase = get(), userMapperToUi = get()) }

    factory {
        ListUserUiDataMapper()
    }
}