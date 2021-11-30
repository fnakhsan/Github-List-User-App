//package com.example.githubuser2.fragment
//
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.viewModels
//import androidx.recyclerview.widget.DividerItemDecoration
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.githubuser2.FollowAdapter
//import com.example.githubuser2.data.FollowResponseItem
//import com.example.githubuser2.databinding.FragmentFollowerBinding
//import com.example.githubuser2.viewmodel.FollowViewModel
//
//class FollowerFragment : Fragment() {
//    private lateinit var followerFragmentBinding: FragmentFollowerBinding
//    private val followerViewModel by viewModels<FollowViewModel>()
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        followerFragmentBinding = FragmentFollowerBinding.inflate(inflater, container, false)
//        return followerFragmentBinding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//        followerFragmentBinding.rvFollower.layoutManager = layoutManager
//        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
//        followerFragmentBinding.rvFollower.addItemDecoration(itemDecoration)
//
//        followerViewModel.followerResponse.observe(viewLifecycleOwner, { follower ->
//            setFollowers(follower)
//        })
//    }
//
//    private fun setFollowers(follower: List<FollowResponseItem>?) {
//        val adapter = follower?.let { FollowAdapter(it) }
//        followerFragmentBinding.rvFollower.adapter = adapter
//    }
//}