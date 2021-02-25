package com.choala.recruitementappdemo.ui.albumList.model

import com.choala.recruitementappdemo.R
import com.choala.recruitementappdemo.domain.album.AlbumModel
import com.choala.recruitementappdemo.ui.common.AndroidStringWrapper

data class ListAlbumUiModel(
    val contentUiModel: ListAlbumContentUiModel? = null,
    val errorUiModel: ListAlbumErrorUiModel? = null,
    val toolBarUiModel: ListAlbumToolBarUiModel
)

data class ListAlbumToolBarUiModel(
    val toolbarTextTitle: AndroidStringWrapper
)

data class ListAlbumContentUiModel(
    val listAlbums: List<AlbumModel>
)

sealed class ListAlbumErrorUiModel(val headerText: AndroidStringWrapper) {
    sealed class PageError(
        header: AndroidStringWrapper,
        val message: AndroidStringWrapper,
        val button: AndroidStringWrapper
    ) : ListAlbumErrorUiModel(header) {
        object NetworkError : ListAlbumErrorUiModel.PageError(
            AndroidStringWrapper(R.string.generic_error_network_header),
            AndroidStringWrapper(R.string.generic_error_network_message),
            AndroidStringWrapper(R.string.generic_error_network_button)
        )

        object TimeOutError : ListAlbumErrorUiModel.PageError(
            AndroidStringWrapper(R.string.generic_error_timeout_header),
            AndroidStringWrapper(R.string.generic_error_timeout_message),
            AndroidStringWrapper(R.string.generic_error_timeout_button)
        )

        object EmptyResource : ListAlbumErrorUiModel.PageError(
            AndroidStringWrapper(R.string.generic_error_empty_header),
            AndroidStringWrapper(R.string.generic_error_empty_message),
            AndroidStringWrapper(R.string.generic_error_empty_button)
        )

        object UnknownError : ListAlbumErrorUiModel.PageError(
            AndroidStringWrapper(R.string.generic_error_unknow_header),
            AndroidStringWrapper(R.string.generic_error_unknow_message),
            AndroidStringWrapper(R.string.generic_error_unknow_button)
        )
    }
}