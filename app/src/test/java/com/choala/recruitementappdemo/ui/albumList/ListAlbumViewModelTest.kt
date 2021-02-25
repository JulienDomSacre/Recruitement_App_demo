package com.choala.recruitementappdemo.ui.albumList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.choala.recruitementappdemo.R
import com.choala.recruitementappdemo.coroutineHelper.CoroutineTestRules
import com.choala.recruitementappdemo.domain.album.AlbumError
import com.choala.recruitementappdemo.domain.album.AlbumModel
import com.choala.recruitementappdemo.domain.album.AlbumUseCase
import com.choala.recruitementappdemo.domain.common.ResultState
import com.choala.recruitementappdemo.ui.albumList.mapper.ListAlbumUiDataMapper
import com.choala.recruitementappdemo.ui.albumList.model.ListAlbumContentUiModel
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
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class ListAlbumViewModelTest {

    @get:Rule
    val rule = CoroutineTestRules()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var albumUseCase: AlbumUseCase

    @Mock
    lateinit var albumMapper: ListAlbumUiDataMapper
    private val albumObserver = mock<Observer<UiScreen<ListAlbumUiModel>>>()

    private lateinit var albumViewModel: ListAlbumViewModel

    private val userId = 1

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        albumViewModel = ListAlbumViewModel(albumUseCase, albumMapper).apply {
            uiLiveData.observeForever(albumObserver)
        }
    }

    @Test
    fun testFetchAlbums() = rule.dispatcher.runBlockingTest {
        val resultUi = UiScreen.success(
            ListAlbumUiModel(
                contentUiModel = getAlbumContent(),
                toolBarUiModel = DEFAULT_TOOLBAR
            )
        )

        val resultUseCase = ResultState.Success(getAlbumUseCase())
        val channel = Channel<ResultState<List<AlbumModel>, AlbumError>>()
        val flow = channel.consumeAsFlow()

        doReturn(flow).whenever(albumUseCase).getAlbums(userId)

        launch { channel.send(resultUseCase) }

        albumViewModel.fetchAlbums(userId)

        albumObserver.inOrder {
            verify(albumObserver).onChanged(
                UiScreen(
                    state = ViewState.LOADING,
                    data = DEFAULT_LOADING_UI_DATA
                )
            )
            verify(albumObserver).onChanged(resultUi)
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