package com.choala.recruitementappdemo.ui.photoList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.choala.recruitementappdemo.R
import com.choala.recruitementappdemo.domain.common.ResultState
import com.choala.recruitementappdemo.domain.photo.PhotoError
import com.choala.recruitementappdemo.domain.photo.PhotoModel
import com.choala.recruitementappdemo.domain.photo.PhotoUseCase
import com.choala.recruitementappdemo.ui.common.AndroidStringWrapper
import com.choala.recruitementappdemo.ui.common.UiScreen
import com.choala.recruitementappdemo.ui.common.ViewState
import com.choala.recruitementappdemo.ui.photoList.mapper.ListPhotoUiDataMapper
import com.choala.recruitementappdemo.ui.photoList.model.ListPhotoErrorUiModel
import com.choala.recruitementappdemo.ui.photoList.model.ListPhotoToolBarUiModel
import com.choala.recruitementappdemo.ui.photoList.model.ListPhotoUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ListPhotoViewModel(
    private val photoUseCase: PhotoUseCase,
    private val photoMapperToUi: ListPhotoUiDataMapper
) : ViewModel() {
    private val uiMutableLiveData = MutableLiveData(
        UiScreen.loading(
            ListPhotoUiModel(toolBarUiModel = DEFAULT_TOOLBAR)
        )
    )
    val uiLiveData: LiveData<UiScreen<ListPhotoUiModel>>
        get() = uiMutableLiveData

    fun fetchPhotos(albumId: Int) {
        postLoadingState()
        viewModelScope.launch(Dispatchers.IO) {
            photoUseCase.getPhotos(albumId).collect { result ->
                uiMutableLiveData.postValue(mapUiScreen(result))
            }
        }
    }

    private fun mapUiScreen(result: ResultState<List<PhotoModel>, PhotoError>): UiScreen<ListPhotoUiModel> {
        return when (result) {
            is ResultState.Success -> UiScreen.success(photoMapperToUi.mapToUi(result.data))
            is ResultState.Error -> {
                val error = getErrorFromResult(result.error)
                UiScreen.error(
                    ListPhotoUiModel(
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

    private fun getErrorFromResult(error: PhotoError): ListPhotoErrorUiModel {
        return when (error) {
            PhotoError.EmptyResource -> ListPhotoErrorUiModel.PageError.EmptyResource
            PhotoError.NoNetworkError -> ListPhotoErrorUiModel.PageError.NetworkError
            PhotoError.TimeOutError -> ListPhotoErrorUiModel.PageError.TimeOutError
            is PhotoError.UnknownError -> ListPhotoErrorUiModel.PageError.UnknownError
        }
    }

    companion object {
        val DEFAULT_TOOLBAR = ListPhotoToolBarUiModel(
            toolbarTextTitle = AndroidStringWrapper(R.string.photo_toolbar_title)
        )
    }
}