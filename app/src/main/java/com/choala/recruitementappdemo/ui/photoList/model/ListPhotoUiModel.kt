package com.choala.recruitementappdemo.ui.photoList.model

import com.choala.recruitementappdemo.R
import com.choala.recruitementappdemo.domain.album.AlbumModel
import com.choala.recruitementappdemo.domain.photo.PhotoModel
import com.choala.recruitementappdemo.ui.common.AndroidStringWrapper

data class ListPhotoUiModel(
    val contentUiModel: ListPhotoContentUiModel? = null,
    val errorUiModel: ListPhotoErrorUiModel? = null,
    val toolBarUiModel: ListPhotoToolBarUiModel
)

data class ListPhotoToolBarUiModel(
    val isImageVisible: Boolean,
    val isSearchVisible: Boolean,
    val toolbarTextTitle: AndroidStringWrapper
)

data class ListPhotoContentUiModel(
    val listAlbums: List<PhotoModel>
)

sealed class ListPhotoErrorUiModel(val headerText: AndroidStringWrapper) {
    sealed class PageError(
        header: AndroidStringWrapper,
        val message: AndroidStringWrapper,
        val button: AndroidStringWrapper
    ) : ListPhotoErrorUiModel(header) {
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