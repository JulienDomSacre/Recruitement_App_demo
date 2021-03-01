package com.choala.recruitementappdemo.ui.albumList

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.map
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.choala.recruitementappdemo.R
import com.choala.recruitementappdemo.ui.albumList.model.ListAlbumContentUiModel
import com.choala.recruitementappdemo.ui.albumList.model.ListAlbumErrorUiModel
import com.choala.recruitementappdemo.ui.albumList.model.ListAlbumToolBarUiModel
import com.choala.recruitementappdemo.ui.common.ViewState
import com.choala.recruitementappdemo.ui.util.visibilityIsRequested
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_albumlist.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListAlbumFragment : Fragment(R.layout.fragment_albumlist) {
    private val viewModel: ListAlbumViewModel by viewModel()
    private val args: ListAlbumFragmentArgs by navArgs()
    private var snackbar: Snackbar? = null
    private val viewAdapter = ListAlbumAdapter { albumSelected ->
        albumSelected.id.let {
            val toAlbum = ListAlbumFragmentDirections.actionListAlbumFragmentToListPhotoFragment(it)
            findNavController().navigate(toAlbum)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeUiData()
        viewModel.fetchAlbums(args.id)
    }

    override fun onDetach() {
        snackbar?.dismiss()
        super.onDetach()
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

    private fun setToolbarContent(listAlbumToolBarUiModel: ListAlbumToolBarUiModel?) {
        if (activity is AppCompatActivity) {
            val toolbar = (activity as AppCompatActivity).supportActionBar
            toolbar?.title =
                listAlbumToolBarUiModel?.toolbarTextTitle?.getConcatenateString(requireContext())
        }
    }

    private fun displayListAlbum(listAlbumContentUiModel: ListAlbumContentUiModel?) {
        listAlbumContentUiModel?.let {
            viewAdapter.updateValues(it.listAlbums)
        }
    }

    private fun updateErrorContent(errorContent: ListAlbumErrorUiModel?) {
        when (errorContent) {
            ListAlbumErrorUiModel.PageError.NetworkError,
            ListAlbumErrorUiModel.PageError.TimeOutError -> {
                val errorData = errorContent as ListAlbumErrorUiModel.PageError
                snackbar = Snackbar.make(
                    const_albumList_container,
                    errorData.message.getConcatenateString(requireContext()),
                    Snackbar.LENGTH_INDEFINITE
                ).setAction(errorData.button.getConcatenateString(requireContext())) {
                    viewModel.fetchAlbums(args.id)
                }
                snackbar!!.show()
            }
            ListAlbumErrorUiModel.PageError.EmptyResource,
            ListAlbumErrorUiModel.PageError.UnknownError -> {
                val errorData = errorContent as ListAlbumErrorUiModel.PageError
                snackbar = Snackbar.make(
                    const_albumList_container,
                    errorData.message.getConcatenateString(requireContext()),
                    Snackbar.LENGTH_INDEFINITE
                ).setAction(errorData.button.getConcatenateString(requireContext())) {
                    activity?.finish()
                }
                snackbar!!.show()
            }
        }
    }

    private fun updateScreenState(viewState: ViewState) {
        pb_albumList_loading.visibilityIsRequested(viewState == ViewState.LOADING)
        rv_albumList_albums.visibilityIsRequested(viewState == ViewState.SUCCESS)
    }

    private fun initRecyclerView() {
        rv_albumList_albums.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = viewAdapter
        }
    }

}