package com.example.githubuser3.ui.follow

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser3.data.Resource
import com.example.githubuser3.data.model.FollowModel
import com.example.githubuser3.databinding.FragmentFollowBinding
import com.example.githubuser3.ui.adapter.FollowAdapter
import com.example.githubuser3.ui.detail.DetailActivity
import com.example.githubuser3.ui.detail.DetailViewModel
import com.example.githubuser3.ui.home.HomeFragment
import com.example.githubuser3.util.ViewModelFactory

class FollowFragment : Fragment() {
    private lateinit var binding: FragmentFollowBinding
//    private val followViewModel by viewModels<FollowViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvFollow.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        binding.rvFollow.addItemDecoration(itemDecoration)

        val argsName = this.arguments?.getString(EXTRA_NAME)
        val argsNumber = this.arguments?.getInt(ARG_SECTION_NUMBER)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val followViewModel: FollowViewModel by viewModels {
            factory
        }
        if (argsName != null) {
            Log.d(TAG, "$argsName")
            when (argsNumber) {
                1 -> {

                    followViewModel.getFollower(argsName).observe(viewLifecycleOwner) {
                        when (it) {
                            is Resource.Loading -> showLoading(true)
                            is Resource.Error -> {
                                Log.d(HomeFragment.TAG, it.error)
                                showLoading(false)
                            }
                            is Resource.Success -> {
                                setFollowers(it.data)
                                showLoading(false)
                            }
                        }
                    }

                    Log.d(TAG, "$argsNumber")
                }

                2 -> {

                    followViewModel.getFollowing(argsName).observe(viewLifecycleOwner) {
                        when (it) {
                            is Resource.Loading -> showLoading(true)
                            is Resource.Error -> {
                                Log.d(HomeFragment.TAG, it.error)
                                showLoading(false)
                            }
                            is Resource.Success -> {
                                setFollowing(it.data)
                                showLoading(false)
                            }
                        }

                    }
                    Log.d(TAG, "$argsNumber")
                }
            }
        }

    }

    private fun showLoading(it: Boolean?) {
        binding.progressBar.visibility = if (it == true) View.VISIBLE else View.GONE
    }

    private fun setFollowers(follower: List<FollowModel>?) {
        val adapter = follower?.let { FollowAdapter(it) }
        adapter?.setOnItemClickCallback(object : FollowAdapter.OnItemClickCallback {
            override fun onItemClicked(data: FollowModel) {
                val intent = Intent(this@FollowFragment.context, DetailActivity::class.java)
                intent.putExtra("extra_username", data.login)
                startActivity(intent)
            }
        })
        binding.rvFollow.adapter = adapter
    }

    private fun setFollowing(following: List<FollowModel>?) {
        val adapter = following?.let { FollowAdapter(it) }
        adapter?.setOnItemClickCallback(object : FollowAdapter.OnItemClickCallback {
            override fun onItemClicked(data: FollowModel) {
                val intent = Intent(this@FollowFragment.context, DetailActivity::class.java)
                intent.putExtra("extra_username", data.login)
                startActivity(intent)
            }
        })
        binding.rvFollow.adapter = adapter
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        private const val EXTRA_NAME = "username"
        private const val TAG = "Follow"

        @JvmStatic
        fun newInstance(index: Int, username: String) =
            FollowFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, index)
                    putString(EXTRA_NAME, username)
                }
            }
    }
}