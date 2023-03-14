package com.example.githubuser3.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubuser3.data.datastore.SettingPreferences
import com.example.githubuser3.ui.setting.SettingViewModel

class SettingFactory(private val preferences: SettingPreferences): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(preferences) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}