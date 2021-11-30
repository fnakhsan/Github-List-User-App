package com.example.githubuser2.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser2.DetailActivity
import com.example.githubuser2.FollowAdapter
import com.example.githubuser2.SectionsPagerAdapter
import com.example.githubuser2.data.FollowResponseItem
import com.example.githubuser2.databinding.FragmentFollowBinding
import com.example.githubuser2.viewmodel.FollowViewModel

class FollowFragment : Fragment() {
    private lateinit var followFragmentBinding: FragmentFollowBinding
    private val followViewModel by viewModels<FollowViewModel>()

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        followFragmentBinding = FragmentFollowBinding.inflate(layoutInflater, container, false)
        return followFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        followFragmentBinding.rvFollow.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        followFragmentBinding.rvFollow.addItemDecoration(itemDecoration)

        val argsName = this.arguments?.getString(EXTRA_NAME)
        val argsNumber = this.arguments?.getInt(ARG_SECTION_NUMBER)

        if (argsName != null) {
            followViewModel.followerUser(argsName)
            followViewModel.followingUser(argsName)
            Log.d(TAG, "$argsName")
        }

//        followViewModel.followerUser("fnakhsan")
//        followViewModel.followingUser("fnakhsan")

        when (argsNumber) {
            1 -> {
                followViewModel.followerResponse.observe(viewLifecycleOwner, { follower ->
                    setFollowers(follower)
                })
                Log.d(TAG, "$argsNumber")
            }

            2 -> {
                followViewModel.followingResponse.observe(viewLifecycleOwner, { following ->
                    setfollowing(following)
                })
                Log.d(TAG, "$argsNumber")
            }
        }

    }

    private fun setFollowers(follower: List<FollowResponseItem>?) {
        val adapter = follower?.let { FollowAdapter(it) }
        followFragmentBinding.rvFollow.adapter = adapter
    }

    private fun setfollowing(following: List<FollowResponseItem>?) {
        val adapter = following?.let { FollowAdapter(it) }
        followFragmentBinding.rvFollow.adapter = adapter
    }
}