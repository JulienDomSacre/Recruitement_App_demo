package com.choala.recruitementappdemo.domain.di

import com.choala.recruitementappdemo.domain.album.AlbumUseCase
import com.choala.recruitementappdemo.domain.photo.PhotoUseCase
import com.choala.recruitementappdemo.domain.user.UserUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { AlbumUseCase(repo = get()) }
    factory { PhotoUseCase(repo = get()) }
    factory { UserUseCase(repo = get()) }
}
