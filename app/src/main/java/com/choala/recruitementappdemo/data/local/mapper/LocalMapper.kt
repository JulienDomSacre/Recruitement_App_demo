package com.choala.recruitementappdemo.data.local.mapper

import com.choala.recruitementappdemo.data.local.entity.AlbumEntity
import com.choala.recruitementappdemo.data.local.entity.PhotoEntity
import com.choala.recruitementappdemo.data.local.entity.UserEntity
import com.choala.recruitementappdemo.domain.album.AlbumModel
import com.choala.recruitementappdemo.domain.photo.PhotoModel
import com.choala.recruitementappdemo.domain.user.UserModel

class LocalMapper {
    fun mapToAlbumModel(
        entity: AlbumEntity
    ): AlbumModel {
        return AlbumModel(
            id = entity.id,
            userId = entity.userId,
            title = entity.title
        )
    }

    fun mapToPhotoModel(
        entity: PhotoEntity
    ): PhotoModel {
        return PhotoModel(
            albumId = entity.albumId,
            title = entity.title,
            url = entity.url,
            thumbnailUrl = entity.thumbnailUrl
        )
    }

    fun mapToUserModel(
        entity: UserEntity
    ): UserModel {
        return UserModel(
            id = entity.id,
            name = entity.name,
            username = entity.username,
            email = entity.email,
            phone = entity.phone,
            website = entity.website

        )
    }
}