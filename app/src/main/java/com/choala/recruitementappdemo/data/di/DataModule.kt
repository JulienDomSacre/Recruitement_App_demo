package com.choala.recruitementappdemo.data.di

import com.choala.recruitementappdemo.data.Repository
import com.choala.recruitementappdemo.domain.repository.IRepository
import org.koin.dsl.module

val dataModule = module {
    single<IRepository> {
        Repository(
            remoteDataSource = get(),
            localAlbumDao = get(),
            localPhotoDao = get(),
            localUserDao = get(),
            mapToEntity = get(),
            mapToModel = get()
        )
    }
}
