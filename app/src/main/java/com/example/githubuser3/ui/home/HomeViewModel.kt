package com.example.githubuser3.ui.home

import androidx.lifecycle.ViewModel
import com.example.githubuser3.data.Repository


class HomeViewModel(private val repository: Repository): ViewModel() {

    fun searchUser(githubName: String) = repository.findUser(githubName)

    companion object {
        private const val TAG = "HomeViewModel"
    }
}