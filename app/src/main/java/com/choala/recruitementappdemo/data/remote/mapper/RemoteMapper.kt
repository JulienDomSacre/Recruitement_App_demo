package com.choala.recruitementappdemo.data.remote.mapper

import com.choala.recruitementappdemo.data.local.entity.AlbumEntity
import com.choala.recruitementappdemo.data.local.entity.PhotoEntity
import com.choala.recruitementappdemo.data.local.entity.UserEntity
import com.choala.recruitementappdemo.data.remote.response.AlbumResponse
import com.choala.recruitementappdemo.data.remote.response.PhotoResponse
import com.choala.recruitementappdemo.data.remote.response.UserResponse

class RemoteMapper {
    fun mapToAlbumEntity(
        response: AlbumResponse
    ): AlbumEntity {
        return AlbumEntity(
            id = response.id,
            userId = response.userId,
            title = response.title
        )
    }

    fun mapToPhotoEntity(
        response: PhotoResponse
    ): PhotoEntity {
        return PhotoEntity(
            id = response.id,
            albumId = response.albumId,
            title = response.title,
            url = response.url,
            thumbnailUrl = response.thumbnailUrl
        )
    }

    fun mapToUserEntity(
        response: UserResponse
    ): UserEntity {
        return UserEntity(
            id = response.id,
            name = response.name,
            username = response.username,
            email = response.email,
            phone = response.phone,
            website = response.website

        )
    }
}