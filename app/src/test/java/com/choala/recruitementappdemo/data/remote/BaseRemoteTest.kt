package com.choala.recruitementappdemo.data.remote

import com.choala.recruitementappdemo.data.remote.api.AlbumService
import com.choala.recruitementappdemo.data.remote.api.PhotoService
import com.choala.recruitementappdemo.data.remote.api.UserService
import com.choala.recruitementappdemo.data.remote.response.UserResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

abstract class BaseRemoteTest : KoinTest {
    protected lateinit var mockServer: MockWebServer

    @Before
    open fun setUp() {
        this.configureMockServer()
        this.configureDi()
    }

    @After
    open fun tearDown() {
        this.stopMockServer()
        stopKoin()
    }

    private fun configureDi() {
        startKoin { modules(remoteTestModule) }
    }

    private fun configureMockServer() {
        mockServer = MockWebServer()
        mockServer.start()
    }

    private fun stopMockServer() {
        mockServer.shutdown()
    }

    fun mockHttpResponse(mockServer: MockWebServer, fileName: String, responseCode: Int) =
        mockServer.enqueue(
            MockResponse()
                .setResponseCode(responseCode)
                .setBody(getJson(fileName))
        )

    private fun getJson(path: String): String {
        val uri = this.javaClass.classLoader?.getResource(path)
        val file = File(uri?.path)
        return String(file.readBytes())
    }
}

    val remoteTestModule = module {
        single {
            OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
        }

        single {
            Retrofit.Builder()
                .client(get())
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        factory {
            get<Retrofit>().create(AlbumService::class.java)
        }

        factory {
            get<Retrofit>().create(UserService::class.java)
        }

        factory {
            get<Retrofit>().create(PhotoService::class.java)
        }
    }