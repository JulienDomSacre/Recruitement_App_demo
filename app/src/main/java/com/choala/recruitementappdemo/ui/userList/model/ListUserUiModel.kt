package com.choala.recruitementappdemo.ui.userList.model

import com.choala.recruitementappdemo.R
import com.choala.recruitementappdemo.domain.user.UserModel
import com.choala.recruitementappdemo.ui.common.AndroidStringWrapper

data class ListUserUiModel(
    val contentUiModel: ListUserContentUiModel? = null,
    val errorUiModel: ListUserErrorUiModel? = null,
    val toolBarUiModel: ListUserToolBarUiModel
)

data class ListUserToolBarUiModel(
    val isImageVisible: Boolean,
    val isSearchVisible: Boolean,
    val toolbarTextTitle: AndroidStringWrapper
)

data class ListUserContentUiModel(
    val listUsers: List<UserModel>
)

sealed class ListUserErrorUiModel(val headerText: AndroidStringWrapper) {
    sealed class PageError(
        header: AndroidStringWrapper,
        val title: AndroidStringWrapper,
        val message: AndroidStringWrapper,
        val button: AndroidStringWrapper
    ) : ListUserErrorUiModel(header) {
        object NetworkError : PageError(
            AndroidStringWrapper(R.string.user_error_network_header),
            AndroidStringWrapper(R.string.user_error_network_title),
            AndroidStringWrapper(R.string.user_error_network_message),
            AndroidStringWrapper(R.string.user_error_network_button)
        )

        object TimeOutError : PageError(
            AndroidStringWrapper(R.string.user_error_timeout_header),
            AndroidStringWrapper(R.string.user_error_timeout_title),
            AndroidStringWrapper(R.string.user_error_timeout_message),
            AndroidStringWrapper(R.string.user_error_timeout_button)
        )

        object EmptyResource : PageError(
            AndroidStringWrapper(R.string.user_error_empty_header),
            AndroidStringWrapper(R.string.user_error_empty_title),
            AndroidStringWrapper(R.string.user_error_empty_message),
            AndroidStringWrapper(R.string.user_error_empty_button)
        )

        object UnknownError : PageError(
            AndroidStringWrapper(R.string.user_error_unknow_header),
            AndroidStringWrapper(R.string.user_error_unknow_title),
            AndroidStringWrapper(R.string.user_error_unknow_message),
            AndroidStringWrapper(R.string.user_error_unknow_button)
        )
    }
}
