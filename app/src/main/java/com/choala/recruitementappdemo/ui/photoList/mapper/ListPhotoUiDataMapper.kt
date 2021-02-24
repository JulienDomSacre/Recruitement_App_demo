package com.choala.recruitementappdemo.ui.photoList.mapper

import com.choala.recruitementappdemo.R
import com.choala.recruitementappdemo.domain.photo.PhotoModel
import com.choala.recruitementappdemo.ui.common.AndroidStringWrapper
import com.choala.recruitementappdemo.ui.photoList.model.ListPhotoContentUiModel
import com.choala.recruitementappdemo.ui.photoList.model.ListPhotoToolBarUiModel
import com.choala.recruitementappdemo.ui.photoList.model.ListPhotoUiModel

class ListPhotoUiDataMapper {
    fun mapToUi(data: List<PhotoModel>): ListPhotoUiModel {
        return ListPhotoUiModel(
            contentUiModel = ListPhotoContentUiModel(
                listAlbums = data
            ),
            errorUiModel = null,
            toolBarUiModel = ListPhotoToolBarUiModel(
                toolbarTextTitle = AndroidStringWrapper(R.string.photo_toolbar_title)
            )
        )
    }
}