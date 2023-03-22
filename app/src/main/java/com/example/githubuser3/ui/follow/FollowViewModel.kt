package com.example.githubuser3.ui.follow

import androidx.lifecycle.ViewModel
import com.example.githubuser3.data.Repository

class FollowViewModel(private val repository: Repository): ViewModel() {

    fun getFollower(name: String) = repository.followerUser(name)

    fun getFollowing(name: String) = repository.followingUser(name)
}