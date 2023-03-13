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
import com.example.githubuser3.R
import com.example.githubuser3.data.Resource
import com.example.githubuser3.data.model.FollowModel
import com.example.githubuser3.databinding.FragmentFollowBinding
import com.example.githubuser3.ui.adapter.FollowAdapter
import com.example.githubuser3.ui.detail.DetailActivity
import com.example.githubuser3.ui.home.HomeFragment
import com.example.githubuser3.util.ViewModelFactory

class FollowFragment : Fragment() {
    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(layoutInflater, container, false)
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
                                showLoading(false)
                                Log.d(HomeFragment.TAG, it.error)
                            }
                            is Resource.Success -> {
                                showLoading(false)
                                setFollowers(it.data, argsName)
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
                                showLoading(false)
                                Log.d(HomeFragment.TAG, it.error)
                            }
                            is Resource.Success -> {
                                showLoading(false)
                                setFollowing(it.data, argsName)
                            }
                        }

                    }
                    Log.d(TAG, "$argsNumber")
                }
            }
        }

    }

    private fun showLoading(it: Boolean) {
        binding.apply {
            progressBar.visibility = if (it) View.VISIBLE else View.GONE
            rvFollow.visibility = if (it) View.INVISIBLE else View.VISIBLE
            layoutNotFound.tvNotFound.visibility = View.INVISIBLE
            layoutNotFound.ivNotFound.visibility = View.INVISIBLE
        }
    }

    private fun setFollowers(follower: List<FollowModel>?, name: String) {
        val adapter = follower?.let { FollowAdapter(it) }
        adapter?.setOnItemClickCallback(object : FollowAdapter.OnItemClickCallback {
            override fun onItemClicked(data: FollowModel) {
                val intent = Intent(this@FollowFragment.context, DetailActivity::class.java)
                intent.putExtra("extra_username", data.login)
                startActivity(intent)
            }
        })
        binding.apply {
            rvFollow.adapter = adapter
            if (follower?.size == 0){
                binding.apply {
                    layoutNotFound.tvNotFound.visibility = View.VISIBLE
                    layoutNotFound.tvNotFound.text = getString(R.string.follower_not_found, name)
                    layoutNotFound.ivNotFound.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setFollowing(following: List<FollowModel>?, name: String) {
        val adapter = following?.let { FollowAdapter(it) }
        adapter?.setOnItemClickCallback(object : FollowAdapter.OnItemClickCallback {
            override fun onItemClicked(data: FollowModel) {
                val intent = Intent(this@FollowFragment.context, DetailActivity::class.java)
                intent.putExtra("extra_username", data.login)
                startActivity(intent)
            }
        })
        binding.apply {
            rvFollow.adapter = adapter
            if (following?.size == 0){
                binding.apply {
                    layoutNotFound.tvNotFound.visibility = View.VISIBLE
                    layoutNotFound.tvNotFound.text = getString(R.string.following_not_found, name)
                    layoutNotFound.ivNotFound.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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