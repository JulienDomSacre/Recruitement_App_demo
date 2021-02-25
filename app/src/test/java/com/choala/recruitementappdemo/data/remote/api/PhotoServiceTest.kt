package com.choala.recruitementappdemo.data.remote.api

import com.choala.recruitementappdemo.data.remote.BaseRemoteTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.koin.core.inject

class PhotoServiceTest : BaseRemoteTest() {
    private val service by inject<PhotoService>()

    @Test
    fun getPhotos_repo() = runBlocking {
        val response = service.getPhotoList(id = 1)

        Assert.assertEquals(1, response.first().id)
        Assert.assertEquals(1, response.first().albumId)
        Assert.assertEquals(
            "accusamus beatae ad facilis cum similique qui sunt",
            response.first().title
        )
        Assert.assertEquals(
            "https://via.placeholder.com/600/92c952",
            response.first().url
        )
        Assert.assertEquals(
            "https://via.placeholder.com/150/92c952",
            response.first().thumbnailUrl
        )
    }
}