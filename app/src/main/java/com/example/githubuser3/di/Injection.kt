package com.example.githubuser3.di

import android.content.Context
import com.example.githubuser3.data.Repository
import com.example.githubuser3.data.local.FavoriteDatabase
import com.example.githubuser3.data.network.ApiConfig

object Injection {
    fun provideRepository(context: Context): Repository {
        val apiService = ApiConfig.getApiService()
        val database = FavoriteDatabase.getDatabase(context)
        val dao = database.favoriteDao()
        return Repository.getInstance(apiService, dao)
    }
}