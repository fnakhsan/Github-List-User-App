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
import com.example.githubuser2.UserAdapter
import com.example.githubuser2.data.UserResponse
import com.example.githubuser2.databinding.FragmentHomeBinding
import com.example.githubuser2.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var homeFragmentBinding: FragmentHomeBinding
    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeFragmentBinding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return homeFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        homeFragmentBinding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        homeFragmentBinding.rvUser.addItemDecoration(itemDecoration)

        homeViewModel.searchResponse.observe(viewLifecycleOwner, { search ->
            setListUsers(search)
        })

        homeViewModel.isLoading.observe(viewLifecycleOwner, {
            showLoading(it)
        })

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
        val adapter = search?.let { UserAdapter(it) }
        adapter?.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserResponse) {
                val toDetailFragment = HomeFragmentDirections.actionHomeFragmentToDetailActivity()
                toDetailFragment.username = data.login
                findNavController().navigate(toDetailFragment)
            }
        })
        homeFragmentBinding.rvUser.adapter = adapter
    }

    private fun showLoading(it: Boolean) {
        homeFragmentBinding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
    }
}