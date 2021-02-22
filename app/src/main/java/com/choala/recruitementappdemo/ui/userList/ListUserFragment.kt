package com.choala.recruitementappdemo.ui.userList

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.map
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.choala.recruitementappdemo.R
import com.choala.recruitementappdemo.ui.common.ViewState
import com.choala.recruitementappdemo.ui.userList.model.ListUserContentUiModel
import com.choala.recruitementappdemo.ui.userList.model.ListUserErrorUiModel
import com.choala.recruitementappdemo.ui.userList.model.ListUserToolBarUiModel
import com.choala.recruitementappdemo.ui.util.visibilityIsRequested
import kotlinx.android.synthetic.main.fragment_userlist.*
import kotlinx.android.synthetic.main.include_page_error.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListUserFragment : Fragment(R.layout.fragment_userlist) {
    private val viewModel: ListUserViewModel by viewModel()
    private val viewAdapter = ListUserAdapter { userSelected ->
        userSelected.id.let {
            val toAlbum = ListUserFragmentDirections.actionListUserFragmentToListAlbumFragment(it)
            findNavController().navigate(toAlbum)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeUiData()
        viewModel.fetchUsers()
    }

    private fun observeUiData() {
        viewModel.uiLiveData.map { it.state }.observe(viewLifecycleOwner, ::updateScreenState)
        viewModel.uiLiveData.map { it.data?.errorUiModel }
            .observe(viewLifecycleOwner, ::updateErrorContent)
        viewModel.uiLiveData.map { it.data?.contentUiModel }
            .observe(viewLifecycleOwner, ::displayListUser)
        viewModel.uiLiveData.map { it.data?.toolBarUiModel }
            .observe(viewLifecycleOwner, ::setToolbarContent)

    }

    private fun setToolbarContent(listUserToolBarUiModel: ListUserToolBarUiModel?) {
        //TODO("Not yet implemented")
    }

    private fun displayListUser(listUserContentUiModel: ListUserContentUiModel?) {
        listUserContentUiModel?.let {
            viewAdapter.updateValues(it.listUsers)
        }
    }

    private fun updateErrorContent(errorContent: ListUserErrorUiModel?) {
        when (errorContent) {
            ListUserErrorUiModel.PageError.NetworkError,
            ListUserErrorUiModel.PageError.TimeOutError -> {
                bt_pageError_action.setOnClickListener { viewModel.fetchUsers() }
                renderErrorView(errorContent as ListUserErrorUiModel.PageError)
            }
            ListUserErrorUiModel.PageError.EmptyResource,
            ListUserErrorUiModel.PageError.UnknownError -> {
                bt_pageError_action.setOnClickListener { activity?.finish() }
                renderErrorView(errorContent as ListUserErrorUiModel.PageError)
            }
        }
    }

    private fun renderErrorView(pageError: ListUserErrorUiModel.PageError) {
        tv_pageError_title.text = pageError.title.getConcatenateString(requireContext())
        tv_pageError_message.text = pageError.message.getConcatenateString(requireContext())
        bt_pageError_action.text = pageError.button.getConcatenateString(requireContext())
    }

    private fun updateScreenState(viewState: ViewState) {
        pb_userList_loading.visibilityIsRequested(viewState == ViewState.LOADING)
        fl_userList_errorLayout.visibilityIsRequested(viewState == ViewState.ERROR)
        rv_userList_users.visibilityIsRequested(viewState == ViewState.SUCCESS)
    }

    private fun initRecyclerView() {
        rv_userList_users.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = viewAdapter
        }
    }
}