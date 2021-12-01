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
import com.example.githubuser2.databinding.FragmentFollowerBinding
import com.example.githubuser2.viewmodel.FollowViewModel

class FollowerFragment : Fragment() {
    private var _followerFragmentBinding: FragmentFollowerBinding? = null
    private val followerFragmentBinding get() = _followerFragmentBinding!!
    private val followerViewModel by viewModels<FollowViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _followerFragmentBinding = FragmentFollowerBinding.inflate(inflater, container, false)
        return _followerFragmentBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        followerFragmentBinding.rvFollower.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        followerFragmentBinding.rvFollower.addItemDecoration(itemDecoration)

        val dataFromBundle = arguments?.getString(DetailActivity.EXTRA_NAME).toString()
        followerViewModel.followerUser(dataFromBundle)

        followerViewModel.followerResponse.observe(viewLifecycleOwner, { follower ->
            setFollowers(follower)
        })
    }

    private fun setFollowers(follower: List<FollowResponseItem>?) {
        val adapter = follower?.let { FollowAdapter(it) }
        followerFragmentBinding.rvFollower.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _followerFragmentBinding = null
    }
}