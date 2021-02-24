package com.choala.recruitementappdemo.ui.photoList

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.map
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.choala.recruitementappdemo.R
import com.choala.recruitementappdemo.ui.albumList.model.ListAlbumErrorUiModel
import com.choala.recruitementappdemo.ui.common.ViewState
import com.choala.recruitementappdemo.ui.photoList.model.ListPhotoContentUiModel
import com.choala.recruitementappdemo.ui.photoList.model.ListPhotoErrorUiModel
import com.choala.recruitementappdemo.ui.photoList.model.ListPhotoToolBarUiModel
import com.choala.recruitementappdemo.ui.util.visibilityIsRequested
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_albumlist.*
import kotlinx.android.synthetic.main.fragment_photolist.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListPhotoFragment : Fragment(R.layout.fragment_photolist) {
    private val viewModel: ListPhotoViewModel by viewModel()
    private val args: ListPhotoFragmentArgs by navArgs()
    private val viewAdapter = ListPhotoAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeUiData()
        viewModel.fetchPhotos(args.id)
    }

    private fun observeUiData() {
        viewModel.uiLiveData.map { it.state }.observe(viewLifecycleOwner, ::updateScreenState)
        viewModel.uiLiveData.map { it.data?.errorUiModel }
            .observe(viewLifecycleOwner, ::updateErrorContent)
        viewModel.uiLiveData.map { it.data?.contentUiModel }
            .observe(viewLifecycleOwner, ::displayListAlbum)
        viewModel.uiLiveData.map { it.data?.toolBarUiModel }
            .observe(viewLifecycleOwner, ::setToolbarContent)
    }

    private fun setToolbarContent(listPhotoToolBarUiModel: ListPhotoToolBarUiModel?) {
        val toolbar = (activity as AppCompatActivity).supportActionBar
        toolbar?.title =
            listPhotoToolBarUiModel?.toolbarTextTitle?.getConcatenateString(requireContext())
    }

    private fun displayListAlbum(listPhotoContentUiModel: ListPhotoContentUiModel?) {
        listPhotoContentUiModel?.let {
            viewAdapter.updateValues(it.listAlbums)
        }
    }

    private fun updateErrorContent(errorContent: ListPhotoErrorUiModel?) {
        when (errorContent) {
            ListPhotoErrorUiModel.PageError.NetworkError,
            ListPhotoErrorUiModel.PageError.TimeOutError -> {
                val errorData = errorContent as ListAlbumErrorUiModel.PageError
                Snackbar.make(
                    const_albumList_container,
                    errorData.message.getConcatenateString(requireContext()),
                    Snackbar.LENGTH_INDEFINITE
                ).setAction(errorData.button.getConcatenateString(requireContext())) {
                    viewModel.fetchPhotos(args.id)
                }.show()
            }
            ListPhotoErrorUiModel.PageError.EmptyResource,
            ListPhotoErrorUiModel.PageError.UnknownError -> {
                val errorData = errorContent as ListAlbumErrorUiModel.PageError
                Snackbar.make(
                    const_albumList_container,
                    errorData.message.getConcatenateString(requireContext()),
                    Snackbar.LENGTH_INDEFINITE
                ).setAction(errorData.button.getConcatenateString(requireContext())) {
                    activity?.finish()
                }.show()
            }
        }
    }

    private fun updateScreenState(viewState: ViewState) {
        pb_photoList_loading.visibilityIsRequested(viewState == ViewState.LOADING)
        rv_photoList_albums.visibilityIsRequested(viewState == ViewState.SUCCESS)
    }

    private fun initRecyclerView() {
        rv_photoList_albums.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = viewAdapter
        }
    }
}
