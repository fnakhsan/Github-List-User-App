package com.example.githubuser2.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser2.R
import com.example.githubuser2.adapter.UserAdapter
import com.example.githubuser2.data.UserResponse
import com.example.githubuser2.databinding.FragmentHomeBinding
import com.example.githubuser2.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private var _homeFragmentBinding: FragmentHomeBinding? = null
    private val homeFragmentBinding get() = _homeFragmentBinding!!
    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _homeFragmentBinding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return homeFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        homeFragmentBinding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        homeFragmentBinding.rvUser.addItemDecoration(itemDecoration)

        homeViewModel.searchResponse.observe(viewLifecycleOwner) { search ->
            setListUsers(search)
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        homeFragmentBinding.actionSearch.apply {
            queryHint = resources.getString(R.string.search)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    homeViewModel.findUser(query)
                    homeFragmentBinding.actionSearch.clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean = false
            })
        }

    }

    private fun setListUsers(search: List<UserResponse>?) {
        if (search?.size == 0){
            homeFragmentBinding.apply {
                rvUser.visibility = View.INVISIBLE
                tvSearchNotFound.visibility = View.VISIBLE
                ivSearchNotFound.visibility = View.VISIBLE
            }
        } else {
            val adapter = search?.let { UserAdapter(it) }
            adapter?.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
                override fun onItemClicked(data: UserResponse) {
                    val toDetailFragment = HomeFragmentDirections.actionHomeFragmentToDetailActivity()
                    toDetailFragment.username = data.login
                    findNavController().navigate(toDetailFragment)
                }
            })
            homeFragmentBinding.apply {
                rvUser.adapter = adapter
                tvSearchNotFound.visibility = View.INVISIBLE
                ivSearchNotFound.visibility = View.INVISIBLE
                rvUser.visibility = View.VISIBLE
            }
        }
    }

    private fun showLoading(it: Boolean) {
        homeFragmentBinding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _homeFragmentBinding = null
    }
}