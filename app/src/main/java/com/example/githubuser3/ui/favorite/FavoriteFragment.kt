package com.example.githubuser3.ui.favorite

import android.os.Bundle
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
import com.example.githubuser3.data.model.UserModel
import com.example.githubuser3.databinding.FragmentFavoriteBinding
import com.example.githubuser3.ui.adapter.UserAdapter
import com.example.githubuser3.util.ViewModelFactory

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val favoriteViewModel: FavoriteViewModel by viewModels {
            factory
        }

        favoriteViewModel.getAllChanges().observe(viewLifecycleOwner) {
            setListUsers(it)
            if (it.isEmpty()){
                setErrorMessage(true, getString(R.string.add_favorite_user), R.drawable.ic_add_user)
            } else {
                setErrorMessage(false)
            }
        }

        binding.actionSearch.apply {
            queryHint = resources.getString(R.string.search)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    favoriteViewModel.searchFav("%$query%").observe(viewLifecycleOwner) {
                        setListUsers(it)
                        if (it.isEmpty()){
                            setErrorMessage(true, getString(R.string.search_not_found), R.drawable.ic_search_user)
                        } else {
                            setErrorMessage(false)
                        }
                    }
                    clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText == null || newText.isEmpty()) {
                        favoriteViewModel.getAllChanges().observe(viewLifecycleOwner) {
                            setListUsers(it)
                            if (it.isEmpty()){
                                setErrorMessage(true, getString(R.string.add_favorite_user), R.drawable.ic_add_user)
                            } else {
                                setErrorMessage(false)
                            }
                        }
                    }
                    return true
                }
            })
        }
    }

    private fun setListUsers(search: List<UserModel>?) {
        val adapter = search?.let { UserAdapter(it) }
        adapter?.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserModel) {
                val toDetailFragment =
                    FavoriteFragmentDirections.actionFavoriteFragmentToDetailActivity()
                toDetailFragment.username = data.login
                findNavController().navigate(toDetailFragment)
            }
        })
        binding.rvUser.adapter = adapter
    }

    private fun setErrorMessage(
        isError: Boolean,
        message: String = getString(R.string.search_not_found),
        image: Int = R.drawable.ic_search_user
    ) {
        binding.layoutNotFound.apply {
            if (isError) {
                tvNotFound.text = message
                ivNotFound.setImageResource(image)
                tvNotFound.visibility = View.VISIBLE
                ivNotFound.visibility = View.VISIBLE
            } else {
                tvNotFound.visibility = View.INVISIBLE
                ivNotFound.visibility = View.INVISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}