package com.example.githubuser3.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuser3.data.Repository
import com.example.githubuser3.data.model.UserModel
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: Repository) : ViewModel() {

    fun isFavorite(name: String): Boolean = repository.isFavorite(name)

    fun getDetail(githubProfile: String) = repository.detailUser(githubProfile)

    fun setFavUser(user: UserModel) = viewModelScope.launch {
        repository.insert(user)
    }

    fun deleteFavUser(user: UserModel) = viewModelScope.launch {
        repository.delete(user)
    }
}