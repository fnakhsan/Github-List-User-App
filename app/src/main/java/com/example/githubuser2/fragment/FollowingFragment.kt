package com.example.githubuser2.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser2.DetailActivity
import com.example.githubuser2.FollowAdapter
import com.example.githubuser2.data.FollowResponseItem
import com.example.githubuser2.databinding.FragmentFollowingBinding
import com.example.githubuser2.viewmodel.FollowViewModel


class FollowingFragment : Fragment() {
    private var _followingFragmentBinding: FragmentFollowingBinding? = null
    private val followingFragmentBinding get() = _followingFragmentBinding!!
    private val followingViewModel by viewModels<FollowViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _followingFragmentBinding = FragmentFollowingBinding.inflate(inflater, container, false)
        return _followingFragmentBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        followingFragmentBinding.rvFollowing.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        followingFragmentBinding.rvFollowing.addItemDecoration(itemDecoration)

        val dataFromBundle = arguments?.getString(DetailActivity.EXTRA_NAME).toString()
        followingViewModel.followingUser(dataFromBundle)

        followingViewModel.followingResponse.observe(viewLifecycleOwner, { following ->
            setFollowing(following)
        })

    }

    private fun setFollowing(following: List<FollowResponseItem>?) {
        val adapter = following?.let { FollowAdapter(it) }
        followingFragmentBinding.rvFollowing.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _followingFragmentBinding = null
    }
}
