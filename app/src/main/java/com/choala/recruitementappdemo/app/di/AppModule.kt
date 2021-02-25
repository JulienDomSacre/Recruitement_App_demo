package com.choala.recruitementappdemo.app.di

import com.choala.recruitementappdemo.data.di.dataModule
import com.choala.recruitementappdemo.data.di.localModule
import com.choala.recruitementappdemo.data.di.remoteModule
import com.choala.recruitementappdemo.domain.di.domainModule
import com.choala.recruitementappdemo.ui.di.uiModule


val appModule = listOf(
    remoteModule,
    localModule,
    dataModule,
    domainModule,
    uiModule
)