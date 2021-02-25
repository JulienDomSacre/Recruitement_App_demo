package com.choala.recruitementappdemo.ui.userList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.choala.recruitementappdemo.R
import com.choala.recruitementappdemo.domain.common.ResultState
import com.choala.recruitementappdemo.domain.user.UserError
import com.choala.recruitementappdemo.domain.user.UserModel
import com.choala.recruitementappdemo.domain.user.UserUseCase
import com.choala.recruitementappdemo.ui.common.AndroidStringWrapper
import com.choala.recruitementappdemo.ui.common.UiScreen
import com.choala.recruitementappdemo.ui.common.ViewState
import com.choala.recruitementappdemo.ui.userList.mapper.ListUserUiDataMapper
import com.choala.recruitementappdemo.ui.userList.model.ListUserErrorUiModel
import com.choala.recruitementappdemo.ui.userList.model.ListUserToolBarUiModel
import com.choala.recruitementappdemo.ui.userList.model.ListUserUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ListUserViewModel(
    private val userUseCase: UserUseCase,
    private val userMapperToUi: ListUserUiDataMapper
) : ViewModel() {
    private val uiMutableLiveData = MutableLiveData(
        UiScreen.loading(
            ListUserUiModel(toolBarUiModel = DEFAULT_TOOLBAR)
        )
    )
    val uiLiveData: LiveData<UiScreen<ListUserUiModel>>
        get() = uiMutableLiveData

    fun fetchUsers() {
        postLoadingState()
        viewModelScope.launch(Dispatchers.IO) {
            userUseCase.getUsers().collect { result ->
                uiMutableLiveData.postValue(mapUiScreen(result))
            }
        }
    }

    private fun mapUiScreen(result: ResultState<List<UserModel>, UserError>): UiScreen<ListUserUiModel> {
        return when (result) {
            is ResultState.Success -> UiScreen.success(userMapperToUi.mapToUi(result.data))
            is ResultState.Error -> {
                val error = getErrorFromResult(result.error)
                UiScreen.error(
                    ListUserUiModel(
                        errorUiModel = error,
                        toolBarUiModel = DEFAULT_TOOLBAR.copy(
                            toolbarTextTitle = error.headerText
                        )
                    )
                )
            }
        }
    }

    private fun postLoadingState() {
        uiMutableLiveData.value?.let {
            uiMutableLiveData.postValue(
                it.copy(
                    state = ViewState.LOADING,
                    data = it.data?.copy(
                        toolBarUiModel = DEFAULT_TOOLBAR
                    )
                )
            )
        }
    }

    private fun getErrorFromResult(error: UserError): ListUserErrorUiModel {
        return when (error) {
            UserError.EmptyResource -> ListUserErrorUiModel.PageError.EmptyResource
            UserError.NoNetworkError -> ListUserErrorUiModel.PageError.NetworkError
            UserError.TimeOutError -> ListUserErrorUiModel.PageError.TimeOutError
            is UserError.UnknownError -> ListUserErrorUiModel.PageError.UnknownError
        }
    }

    companion object {
        val DEFAULT_TOOLBAR = ListUserToolBarUiModel(
            isImageVisible = false,
            isSearchVisible = false,
            toolbarTextTitle = AndroidStringWrapper(R.string.user_toolbar_title)
        )
    }
}