package com.choala.recruitementappdemo.data.remote.api

import com.choala.recruitementappdemo.data.remote.BaseRemoteTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.koin.core.inject

class UserServiceTest: BaseRemoteTest() {
    private val service by inject<UserService>()

    @Test
    fun getUsers_repo() = runBlocking {
        val response = service.getUserList()

        Assert.assertEquals(1, response.first().id)
        Assert.assertEquals("Leanne Graham", response.first().name)
        Assert.assertEquals("Sincere@april.biz", response.first().email)
        Assert.assertEquals("Bret", response.first().username)
        Assert.assertEquals("Kulas Light", response.first().address.street)
        Assert.assertEquals("1-770-736-8031 x56442", response.first().phone)
        Assert.assertEquals("hildegard.org", response.first().website)
        Assert.assertEquals("Romaguera-Crona", response.first().company.name)
    }
}