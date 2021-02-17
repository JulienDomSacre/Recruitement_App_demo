package com.choala.recruitementappdemo.app.di

import com.choala.recruitementappdemo.data.di.dataModule
import com.choala.recruitementappdemo.data.di.localModule
import com.choala.recruitementappdemo.data.di.remoteModule


val appModule = listOf(
    remoteModule,
    localModule,
    dataModule
)