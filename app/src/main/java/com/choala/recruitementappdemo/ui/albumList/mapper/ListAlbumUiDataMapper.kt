package com.choala.recruitementappdemo.ui.albumList.mapper

import com.choala.recruitementappdemo.R
import com.choala.recruitementappdemo.domain.album.AlbumModel
import com.choala.recruitementappdemo.ui.albumList.model.ListAlbumContentUiModel
import com.choala.recruitementappdemo.ui.albumList.model.ListAlbumToolBarUiModel
import com.choala.recruitementappdemo.ui.albumList.model.ListAlbumUiModel
import com.choala.recruitementappdemo.ui.common.AndroidStringWrapper

class ListAlbumUiDataMapper {
    fun mapToUi(data: List<AlbumModel>): ListAlbumUiModel {
        return ListAlbumUiModel(
            contentUiModel = ListAlbumContentUiModel(
                listAlbums = data
            ),
            errorUiModel = null,
            toolBarUiModel = ListAlbumToolBarUiModel(
                isImageVisible = false,
                isSearchVisible = true,
                toolbarTextTitle = AndroidStringWrapper(R.string.app_name)
            )
        )
    }
}