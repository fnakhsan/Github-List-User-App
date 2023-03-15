package com.example.githubuser3.ui.follow

import android.content.Intent
import android.os.Bundle
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
            when (argsNumber) {
                1 -> {
                    followViewModel.getFollower(argsName).observe(viewLifecycleOwner) {
                        when (it) {
                            is Resource.Loading -> showLoading(true)
                            is Resource.Error -> {
                                showLoading(false)
                            }
                            is Resource.Success -> {
                                showLoading(false)
                                setFollowers(it.data, argsName)
                            }
                        }
                    }
                }

                2 -> {
                    followViewModel.getFollowing(argsName).observe(viewLifecycleOwner) {
                        when (it) {
                            is Resource.Loading -> showLoading(true)
                            is Resource.Error -> {
                                showLoading(false)
                            }
                            is Resource.Success -> {
                                showLoading(false)
                                setFollowing(it.data, argsName)
                            }
                        }

                    }
                }
            }
        }

    }

    private fun showLoading(it: Boolean) {
        binding.apply {
            progressBar.visibility = if (it) View.VISIBLE else View.GONE
            rvFollow.visibility = if (it) View.INVISIBLE else View.VISIBLE
            setLayoutNotFoundVisibility(false)
        }
    }

    private fun setFollowers(follower: List<FollowModel>?, name: String) {
        if (follower?.size == 0) {
            binding.layoutNotFound.tvNotFound.text = getString(R.string.follower_not_found, name)
            setLayoutNotFoundVisibility(true)
        } else {
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
    }

    private fun setFollowing(following: List<FollowModel>?, name: String) {
        if (following?.size == 0) {
            binding.layoutNotFound.tvNotFound.text = getString(R.string.following_not_found, name)
            setLayoutNotFoundVisibility(true)
        } else {
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
    }

    private fun setLayoutNotFoundVisibility(isVisible: Boolean) {
        binding.layoutNotFound.apply {
            if (isVisible){
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

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        private const val EXTRA_NAME = "username"

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