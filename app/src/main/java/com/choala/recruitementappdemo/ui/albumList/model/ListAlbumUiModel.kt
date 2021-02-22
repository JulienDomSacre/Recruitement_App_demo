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
    val isImageVisible: Boolean,
    val isSearchVisible: Boolean,
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
        object NetworkError : PageError(
            AndroidStringWrapper(R.string.user_error_network_header),
            AndroidStringWrapper(R.string.user_error_network_title),
            AndroidStringWrapper(R.string.user_error_network_message)
        )

        object TimeOutError : PageError(
            AndroidStringWrapper(R.string.user_error_timeout_header),
            AndroidStringWrapper(R.string.user_error_timeout_title),
            AndroidStringWrapper(R.string.user_error_timeout_message)
        )

        object EmptyResource : PageError(
            AndroidStringWrapper(R.string.user_error_empty_header),
            AndroidStringWrapper(R.string.user_error_empty_title),
            AndroidStringWrapper(R.string.user_error_empty_message)
        )

        object UnknownError : PageError(
            AndroidStringWrapper(R.string.user_error_unknow_header),
            AndroidStringWrapper(R.string.user_error_unknow_title),
            AndroidStringWrapper(R.string.user_error_unknow_message)
        )
    }
}