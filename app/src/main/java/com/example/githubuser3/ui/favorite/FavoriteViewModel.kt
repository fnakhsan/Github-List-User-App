package com.example.githubuser3.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.githubuser3.data.local.FavoriteRepository
import com.example.githubuser3.data.model.FavoriteModel

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    private val repository: FavoriteRepository = FavoriteRepository(application)

    fun getAllChanges(): LiveData<List<FavoriteModel>> = repository.getAllChanges()

    suspend fun getAll(): List<FavoriteModel> = repository.getAll()
}