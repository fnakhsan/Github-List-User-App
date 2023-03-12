package com.example.githubuser3.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser3.data.Repository
import com.example.githubuser3.data.model.UserModel

class FavoriteViewModel(private val repository: Repository): ViewModel() {

    fun getAllChanges(): LiveData<List<UserModel>> = repository.getAllChanges()

    fun searchFav(name: String): LiveData<List<UserModel>> = repository.searchFav(name)
}