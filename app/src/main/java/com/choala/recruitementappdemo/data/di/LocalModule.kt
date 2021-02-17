package com.choala.recruitementappdemo.data.di

import androidx.room.Room
import com.choala.recruitementappdemo.data.local.mapper.LocalMapper
import com.choala.recruitementappdemo.data.local.room.DataBase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val localModule = module {

    single {
        Room.databaseBuilder(androidApplication(), DataBase::class.java, "demo.db")
            .build()
    }

    factory { get<DataBase>().albumDao() }
    factory { get<DataBase>().photoDao() }
    factory { get<DataBase>().userDao() }

    factory { LocalMapper() }
}