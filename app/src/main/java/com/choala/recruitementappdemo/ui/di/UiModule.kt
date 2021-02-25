package com.choala.recruitementappdemo.ui.di

import com.choala.recruitementappdemo.ui.albumList.ListAlbumViewModel
import com.choala.recruitementappdemo.ui.albumList.mapper.ListAlbumUiDataMapper
import com.choala.recruitementappdemo.ui.photoList.ListPhotoViewModel
import com.choala.recruitementappdemo.ui.photoList.mapper.ListPhotoUiDataMapper
import com.choala.recruitementappdemo.ui.userList.ListUserViewModel
import com.choala.recruitementappdemo.ui.userList.mapper.ListUserUiDataMapper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { ListUserViewModel(userUseCase = get(), userMapperToUi = get()) }
    viewModel { ListAlbumViewModel(albumUseCase = get(), albumMapperToUi = get()) }
    viewModel { ListPhotoViewModel(photoUseCase = get(), photoMapperToUi = get()) }

    factory {
        ListUserUiDataMapper()
    }

    factory {
        ListAlbumUiDataMapper()
    }

    factory {
        ListPhotoUiDataMapper()
    }
}