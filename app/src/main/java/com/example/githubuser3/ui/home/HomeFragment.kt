package com.example.githubuser3.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser3.R
import com.example.githubuser3.data.Resource
import com.example.githubuser3.data.model.UserModel
import com.example.githubuser3.databinding.FragmentHomeBinding
import com.example.githubuser3.ui.adapter.UserAdapter
import com.example.githubuser3.util.ViewModelFactory

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val homeViewModel: HomeViewModel by viewModels {
            factory
        }

        homeViewModel.searchUser("a").observe(viewLifecycleOwner) {
            Log.d(TAG, "masuk observe")
            when (it) {
                is Resource.Loading -> showLoading(true)
                is Resource.Success -> {
                    Log.d(TAG, it.data.toString())
                    showLoading(false)
                    setListUsers(it.data)
                }
                is Resource.Error -> {
                    Log.d(TAG, it.error)
                    Log.d(TAG, "error")
                    showLoading(false)
                }
            }
            Log.d(TAG, "akhir observe")
        }

        binding.actionSearch.apply {
            queryHint = resources.getString(R.string.search)
            Log.d(TAG, queryHint.toString())
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    homeViewModel.searchUser(query).observe(viewLifecycleOwner) {
                        when (it) {
                            is Resource.Loading -> showLoading(true)
                            is Resource.Error -> {
                                Log.d(TAG, it.error)
                                showLoading(false)
                            }
                            is Resource.Success -> {
                                showLoading(false)
                                setListUsers(it.data)
                            }
                        }
                    }
                    clearFocus()
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
                val toDetailFragment = HomeFragmentDirections.actionHomeFragmentToDetailActivity()
                toDetailFragment.username = data.login
                findNavController().navigate(toDetailFragment)
            }
        })
        binding.apply {
            rvUser.adapter = adapter
            if (search?.size == 0){
                binding.apply {
                    layoutNotFound.tvNotFound.visibility = View.VISIBLE
                    layoutNotFound.ivNotFound.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun showLoading(it: Boolean) {
        binding.apply {
            progressBar.visibility = if (it) View.VISIBLE else View.INVISIBLE
            rvUser.visibility = if (it) View.INVISIBLE else View.VISIBLE
            layoutNotFound.tvNotFound.visibility = View.INVISIBLE
            layoutNotFound.ivNotFound.visibility = View.INVISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "HomeFragment"
    }
}