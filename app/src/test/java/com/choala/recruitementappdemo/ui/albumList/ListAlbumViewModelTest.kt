package com.choala.recruitementappdemo.ui.albumList

import androidx.lifecycle.Observer
import com.choala.recruitementappdemo.R
import com.choala.recruitementappdemo.coroutineHelper.CoroutineTestRules
import com.choala.recruitementappdemo.domain.album.AlbumError
import com.choala.recruitementappdemo.domain.album.AlbumModel
import com.choala.recruitementappdemo.domain.album.AlbumUseCase
import com.choala.recruitementappdemo.domain.common.ResultState
import com.choala.recruitementappdemo.ui.albumList.mapper.ListAlbumUiDataMapper
import com.choala.recruitementappdemo.ui.albumList.model.ListAlbumContentUiModel
import com.choala.recruitementappdemo.ui.albumList.model.ListAlbumErrorUiModel
import com.choala.recruitementappdemo.ui.albumList.model.ListAlbumToolBarUiModel
import com.choala.recruitementappdemo.ui.albumList.model.ListAlbumUiModel
import com.choala.recruitementappdemo.ui.common.AndroidStringWrapper
import com.choala.recruitementappdemo.ui.common.UiScreen
import com.choala.recruitementappdemo.ui.common.ViewState
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class ListAlbumViewModelTest {

    @get:Rule
    val rule = CoroutineTestRules()
    private val albumUseCase= mock<AlbumUseCase>()
    private val  albumMapper = mock<ListAlbumUiDataMapper>()
    private val albumObserver = mock<Observer<UiScreen<ListAlbumUiModel>>>()

    private lateinit var albumViewModel: ListAlbumViewModel
    private val userId = 1

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        albumViewModel = ListAlbumViewModel(albumUseCase, albumMapper).apply {
            uiLiveData.observeForever(albumObserver)
        }

        verify(albumObserver).onChanged(
            UiScreen.loading(DEFAULT_LOADING_UI_DATA)
        )
        reset(albumObserver)
    }

    @After
    fun tearDown(){
        verifyNoMoreInteractions(albumObserver, albumUseCase)
    }

    @Test
    fun fetchAlbums_success() = rule.dispatcher.runBlockingTest {
        val expectedResult = UiScreen.success(
            ListAlbumUiModel(
                contentUiModel = getAlbumContent(),
                toolBarUiModel = DEFAULT_TOOLBAR
            )
        )
        /**GIVEN**/
        //Mock flow
        val resultUseCase = ResultState.Success(getAlbumUseCase())
        val channel = Channel<ResultState<List<AlbumModel>, AlbumError>>()
        val flow = channel.consumeAsFlow()
        doReturn(flow).whenever(albumUseCase).getAlbums(userId)
        launch { channel.send(resultUseCase) }

        //Mock mapper
        given(albumMapper.mapToUi(getAlbumUseCase())).willReturn(
            ListAlbumUiModel(
                contentUiModel = getAlbumContent(),
                errorUiModel = null,
                toolBarUiModel = DEFAULT_TOOLBAR
            )
        )

        /**WHEN**/
        albumViewModel.fetchAlbums(userId)

        /**THEN**/
        albumObserver.inOrder {
            verify().onChanged(
                UiScreen(
                    state = ViewState.LOADING,
                    data = DEFAULT_LOADING_UI_DATA
                )
            )

            verify().onChanged(expectedResult)
        }
        verify(albumUseCase).getAlbums(userId)
    }

    @Test
    fun fetchAlbums_error_noNetwork() = rule.dispatcher.runBlockingTest {
        val expectedResult = UiScreen.error(
            ListAlbumUiModel(
                errorUiModel = ListAlbumErrorUiModel.PageError.NetworkError,
                toolBarUiModel = DEFAULT_TOOLBAR.copy(
                    toolbarTextTitle = AndroidStringWrapper(R.string.generic_error_network_header)
                )
            )
        )

        fetchAlbumsWithSpecificError(AlbumError.NoNetworkError, expectedResult)

    }

    @Test
    fun fetchAlbums_error_timeOut(){
        val expectedResult = UiScreen.error(
            ListAlbumUiModel(
                errorUiModel = ListAlbumErrorUiModel.PageError.TimeOutError,
                toolBarUiModel = DEFAULT_TOOLBAR.copy(
                    toolbarTextTitle = AndroidStringWrapper(R.string.generic_error_timeout_header)
                )
            )
        )

        fetchAlbumsWithSpecificError(AlbumError.TimeOutError, expectedResult)
    }

    @Test
    fun fetchAlbums_error_unknow(){
        val expectedResult = UiScreen.error(
            ListAlbumUiModel(
                errorUiModel = ListAlbumErrorUiModel.PageError.UnknownError,
                toolBarUiModel = DEFAULT_TOOLBAR.copy(
                    toolbarTextTitle = AndroidStringWrapper(R.string.generic_error_unknow_header)
                )
            )
        )

        fetchAlbumsWithSpecificError(AlbumError.UnknownError("test"), expectedResult)
    }

    @Test
    fun fetchAlbums_error_empty(){
        val expectedResult = UiScreen.error(
            ListAlbumUiModel(
                errorUiModel = ListAlbumErrorUiModel.PageError.EmptyResource,
                toolBarUiModel = DEFAULT_TOOLBAR.copy(
                    toolbarTextTitle = AndroidStringWrapper(R.string.generic_error_empty_header)
                )
            )
        )

        fetchAlbumsWithSpecificError(AlbumError.EmptyResource, expectedResult)
    }

    private fun fetchAlbumsWithSpecificError(
        outcomeError: AlbumError,
        uiResult: UiScreen<ListAlbumUiModel>
    ) = rule.dispatcher.runBlockingTest {
        /**GIVEN**/
        val resultUseCase = ResultState.Error(outcomeError)
        val channel = Channel<ResultState<List<AlbumModel>, AlbumError>>()
        val flow = channel.consumeAsFlow()
        doReturn(flow).whenever(albumUseCase).getAlbums(userId)
        launch { channel.send(resultUseCase) }

        /**WHEN**/
        albumViewModel.fetchAlbums(userId)

        /**THEN**/
        albumObserver.inOrder {
            verify(albumObserver).onChanged(
                UiScreen(
                    state = ViewState.LOADING,
                    data = DEFAULT_LOADING_UI_DATA
                )
            )

            verify(albumObserver).onChanged(uiResult)
        }
        verify(albumUseCase).getAlbums(userId)
    }

    private fun getAlbumContent() =
        ListAlbumContentUiModel(
            listOf(AlbumModel(id = 1, userId = 1, title = "title"))
        )

    private fun getAlbumUseCase() =
        listOf(
            AlbumModel(id = 1, userId = 1, title = "title")
        )

    companion object {
        val DEFAULT_TOOLBAR = ListAlbumToolBarUiModel(
            toolbarTextTitle = AndroidStringWrapper(R.string.album_toolbar_title)
        )

        val DEFAULT_LOADING_UI_DATA = ListAlbumUiModel(
            contentUiModel = null,
            errorUiModel = null,
            toolBarUiModel = DEFAULT_TOOLBAR
        )
    }
}