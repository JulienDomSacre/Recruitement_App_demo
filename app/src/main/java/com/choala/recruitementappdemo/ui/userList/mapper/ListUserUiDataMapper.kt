package com.choala.recruitementappdemo.ui.userList.mapper

import com.choala.recruitementappdemo.R
import com.choala.recruitementappdemo.domain.user.UserModel
import com.choala.recruitementappdemo.ui.common.AndroidStringWrapper
import com.choala.recruitementappdemo.ui.userList.model.ListUserContentUiModel
import com.choala.recruitementappdemo.ui.userList.model.ListUserToolBarUiModel
import com.choala.recruitementappdemo.ui.userList.model.ListUserUiModel

class ListUserUiDataMapper {
    fun mapToUi(data: List<UserModel>): ListUserUiModel{
        return ListUserUiModel(
            contentUiModel = ListUserContentUiModel(
                listUsers = data
            ),
            errorUiModel = null,
            toolBarUiModel = ListUserToolBarUiModel(
                isImageVisible = false,
                isSearchVisible = true,
                toolbarTextTitle = AndroidStringWrapper(R.string.app_name)
            )
        )
    }
}
