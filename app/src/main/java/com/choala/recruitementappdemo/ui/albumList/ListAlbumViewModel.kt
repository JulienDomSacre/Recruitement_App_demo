package com.choala.recruitementappdemo.ui.albumList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.choala.recruitementappdemo.R
import com.choala.recruitementappdemo.domain.album.AlbumError
import com.choala.recruitementappdemo.domain.album.AlbumModel
import com.choala.recruitementappdemo.domain.album.AlbumUseCase
import com.choala.recruitementappdemo.domain.common.ResultState
import com.choala.recruitementappdemo.ui.albumList.mapper.ListAlbumUiDataMapper
import com.choala.recruitementappdemo.ui.albumList.model.ListAlbumErrorUiModel
import com.choala.recruitementappdemo.ui.albumList.model.ListAlbumToolBarUiModel
import com.choala.recruitementappdemo.ui.albumList.model.ListAlbumUiModel
import com.choala.recruitementappdemo.ui.common.AndroidStringWrapper
import com.choala.recruitementappdemo.ui.common.UiScreen
import com.choala.recruitementappdemo.ui.common.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ListAlbumViewModel(
    private val albumUseCase: AlbumUseCase,
    private val albumMapperToUi: ListAlbumUiDataMapper
) : ViewModel() {
    private val uiMutableLiveData = MutableLiveData(
        UiScreen.loading(
            ListAlbumUiModel(toolBarUiModel = DEFAULT_TOOLBAR)
        )
    )
    val uiLiveData: LiveData<UiScreen<ListAlbumUiModel>>
        get() = uiMutableLiveData

    fun fetchAlbums(userId: Int) {
        postLoadingState()
        viewModelScope.launch {
            albumUseCase.getAlbums(userId).collect { result ->
                uiMutableLiveData.postValue(mapUiScreen(result))
            }
        }
    }

    private fun mapUiScreen(result: ResultState<List<AlbumModel>, AlbumError>): UiScreen<ListAlbumUiModel> {
        return when (result) {
            is ResultState.Success -> UiScreen.success(albumMapperToUi.mapToUi(result.data))
            is ResultState.Error -> {
                val error = getErrorFromResult(result.error)
                UiScreen.error(
                    ListAlbumUiModel(
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

    private fun getErrorFromResult(error: AlbumError): ListAlbumErrorUiModel {
        return when (error) {
            AlbumError.EmptyResource -> ListAlbumErrorUiModel.PageError.EmptyResource
            AlbumError.NoNetworkError -> ListAlbumErrorUiModel.PageError.NetworkError
            AlbumError.TimeOutError -> ListAlbumErrorUiModel.PageError.TimeOutError
            is AlbumError.UnknownError -> ListAlbumErrorUiModel.PageError.UnknownError
        }
    }

    companion object {
        val DEFAULT_TOOLBAR = ListAlbumToolBarUiModel(
            toolbarTextTitle = AndroidStringWrapper(R.string.album_toolbar_title)
        )
    }
}