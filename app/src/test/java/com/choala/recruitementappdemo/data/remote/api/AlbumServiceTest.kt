package com.choala.recruitementappdemo.data.remote.api

import com.choala.recruitementappdemo.data.remote.BaseRemoteTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.koin.core.inject

class AlbumServiceTest : BaseRemoteTest() {
    private val service by inject<AlbumService>()

    @Test
    fun getAlbums_repo() = runBlocking {
        val response = service.getAlbumList(id = 1)

        Assert.assertEquals(1, response.first().userId)
        Assert.assertEquals(1, response.first().id)
        Assert.assertEquals("quidem molestiae enim", response.first().title)
    }
}
