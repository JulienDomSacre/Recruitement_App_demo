package com.choala.recruitementappdemo.data.local.dao

import android.os.Build
import com.choala.recruitementappdemo.data.local.BaseLocalTest
import com.choala.recruitementappdemo.data.local.entity.UserEntity
import com.choala.recruitementappdemo.data.local.room.user.UserDao
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
class UserDaoTest : BaseLocalTest() {

    private val dao by inject<UserDao>()

    @Test
    fun insertUsers() = runBlocking {
        dao.insertAll(
            listOf(
                UserEntity(
                    id = 1,
                    name = "name",
                    username = "username",
                    email = "email",
                    phone = "phone",
                    website = "website"
                )
            )
        )

        val userList = dao.userList()
        Assert.assertEquals(1, userList.first().size)
    }
}