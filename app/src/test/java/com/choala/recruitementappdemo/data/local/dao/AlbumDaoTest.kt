package com.choala.recruitementappdemo.data.local.dao

import android.os.Build
import com.choala.recruitementappdemo.data.local.BaseLocalTest
import com.choala.recruitementappdemo.data.local.entity.AlbumEntity
import com.choala.recruitementappdemo.data.local.room.album.AlbumDao
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
class AlbumDaoTest : BaseLocalTest() {

    private val dao by inject<AlbumDao>()

    @Test
    fun insertAlbums() = runBlocking {
        dao.insertAll(listOf(AlbumEntity(id = 1, userId = 1, title = "title")))

        val albumList = dao.albumList()
        Assert.assertEquals(1, albumList.first().size)
    }

    @Test
    fun getAlbumWithUserId() = runBlocking {
        dao.insertAll(
            listOf(
                AlbumEntity(id = 1, userId = 1, title = "title"),
                AlbumEntity(id = 5, userId = 2, title = "title2")
            )
        )

        val albumList = dao.getAlbumListByUserId(2).first()
        Assert.assertEquals(1, albumList.size)
        Assert.assertEquals(5, albumList[0].id)
        Assert.assertEquals(2, albumList[0].userId)
        Assert.assertEquals("title2", albumList[0].title)
    }
}