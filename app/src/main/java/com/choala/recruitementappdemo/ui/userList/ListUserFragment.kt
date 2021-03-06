package com.choala.recruitementappdemo.ui.userList

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.map
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.choala.recruitementappdemo.MainActivity
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

    private var toolbarMenuIsVisible = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeUiData()
        viewModel.fetchUsers()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.user_menu, menu)

        val searchView = SearchView((activity as AppCompatActivity).supportActionBar?.themedContext ?: requireContext())
        menu.findItem(R.id.menu_userList_actionSearch).apply {
            setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
            actionView = searchView
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.filterUser(newText)
                return false
            }
        })
        menu.findItem(R.id.menu_userList_actionSearch).isVisible = toolbarMenuIsVisible


        // Associate searchable configuration with the SearchView
        val searchManager = context?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.menu_userList_actionSearch).actionView as SearchView).apply {
            //setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_userList_actionSearch -> Log.d("toto", "menu touch")
        }
        return true
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
        val toolbar = (activity as AppCompatActivity).supportActionBar
        toolbar?.title =
            listUserToolBarUiModel?.toolbarTextTitle?.getConcatenateString(requireContext())
        toolbarMenuIsVisible = listUserToolBarUiModel?.isSearchVisible == true
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