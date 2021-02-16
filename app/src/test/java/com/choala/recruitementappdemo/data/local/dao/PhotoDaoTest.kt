package com.choala.recruitementappdemo.data.local.dao

import android.os.Build
import com.choala.recruitementappdemo.data.local.BaseLocalTest
import com.choala.recruitementappdemo.data.local.entity.PhotoEntity
import com.choala.recruitementappdemo.data.local.room.photo.PhotoDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.inject
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class PhotoDaoTest : BaseLocalTest() {

    private val dao by inject<PhotoDao>()

    @Test
    fun insertPhotos() = runBlocking {
        dao.insertAll(
            listOf(
                PhotoEntity(
                    id = 1,
                    albumId = 1,
                    title = "title",
                    url = "toto",
                    thumbnailUrl = "tata"
                )
            )
        )

        val photoList = dao.photoList()
        Assert.assertEquals(1, photoList.first().size)
    }

    @Test
    fun getPhotoListWithAlbumId() = runBlocking {
        dao.insertAll(
            listOf(
                PhotoEntity(
                    id = 1,
                    albumId = 1,
                    title = "title",
                    url = "toto",
                    thumbnailUrl = "tata"
                ),
                PhotoEntity(
                    id = 15,
                    albumId = 2,
                    title = "title2",
                    url = "toto2",
                    thumbnailUrl = "tata2"
                )
            )
        )

        val photoList = dao.getPhotoListByAlbumId(2).first()
        Assert.assertEquals(1, photoList.size)
        Assert.assertEquals(15, photoList[0].id)
        Assert.assertEquals(2, photoList[0].albumId)
        Assert.assertEquals("title2", photoList[0].title)
        Assert.assertEquals("toto2", photoList[0].url)
        Assert.assertEquals("tata2", photoList[0].thumbnailUrl)
    }
}