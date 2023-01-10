package com.example.githubuser3.ui.main

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
import com.example.githubuser3.R
import com.example.githubuser3.data.model.UserModel
import com.example.githubuser3.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private lateinit var dashboardBinding: FragmentDashboardBinding
    private val homeViewModel by viewModels<DashboardViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dashboardBinding = FragmentDashboardBinding.inflate(layoutInflater, container, false)
        return dashboardBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        dashboardBinding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        dashboardBinding.rvUser.addItemDecoration(itemDecoration)

        homeViewModel.searchResponse.observe(viewLifecycleOwner) { search ->
            setListUsers(search)
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        dashboardBinding.actionSearch.apply {
            queryHint = resources.getString(R.string.search)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    homeViewModel.findUser(query)
                    dashboardBinding.actionSearch.clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean = false
            })
        }
    }

    private fun setListUsers(search: List<UserModel>?) {
        val adapter = search?.let { UserAdapter(it) }
        adapter?.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserModel) {
                val toDetailFragment = DashboardFragmentDirections.actionHomeFragmentToDetailActivity()
                toDetailFragment.username = data.login
                findNavController().navigate(toDetailFragment)
            }
        })
        dashboardBinding.rvUser.adapter = adapter
    }

    private fun showLoading(it: Boolean) {
        dashboardBinding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
    }

    companion object {

    }
}