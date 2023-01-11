package com.example.githubuser3.ui.follow

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser3.ui.adapter.FollowAdapter
import com.example.githubuser3.data.model.FollowModel
import com.example.githubuser3.databinding.FragmentFollowBinding
import com.example.githubuser3.ui.detail.DetailActivity

class FollowFragment : Fragment() {
    private lateinit var binding: FragmentFollowBinding
    private val followViewModel by viewModels<FollowViewModel>()

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

        if (argsName != null) {
            followViewModel.followerUser(argsName)
            followViewModel.followingUser(argsName)
            Log.d(TAG, "$argsName")
        }

        when (argsNumber) {
            1 -> {
                followViewModel.isLoading.observe(viewLifecycleOwner) {
                    showLoading(it)
                }
                followViewModel.followerResponse.observe(viewLifecycleOwner) { follower ->
                    setFollowers(follower)
                }
                Log.d(TAG, "$argsNumber")
            }

            2 -> {
                followViewModel.isLoading.observe(viewLifecycleOwner) {
                    showLoading(it)
                }
                followViewModel.followingResponse.observe(viewLifecycleOwner) { following ->
                    setFollowing(following)
                }
                Log.d(TAG, "$argsNumber")
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
        private const val TAG = "FollowFrag"

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