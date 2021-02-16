package com.choala.recruitementappdemo.data.local

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.choala.recruitementappdemo.data.local.room.DataBase
import org.junit.After
import org.junit.Before
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

abstract class BaseLocalTest : KoinTest {

    private val db: DataBase by inject()

    @Before
    open fun setUp() {
        this.configureDi()
    }

    @After
    open fun tearDown() {
        db.close()
        stopKoin()
    }

    private fun configureDi() {
        startKoin { modules(localTestModule) }
    }

}

val localTestModule = module(override = true) {
    val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    single {
        Room.inMemoryDatabaseBuilder(appContext, DataBase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    factory { get<DataBase>().albumDao() }
    factory { get<DataBase>().photoDao() }
    factory { get<DataBase>().userDao() }
}