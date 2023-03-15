package com.example.githubuser3.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>){

    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map {
            it[THEME_KEY] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit {
            it[THEME_KEY] = isDarkModeActive
        }
    }

    fun getLocaleSetting(): Flow<String>{
        return dataStore.data.map {
            it[LOCALE_KEY] ?: "en"
        }
    }

    suspend fun saveLocaleSetting(localeName: String){
        dataStore.edit {
            it[LOCALE_KEY] = localeName
        }
    }

    companion object {
        private val THEME_KEY = booleanPreferencesKey("theme_setting")
        private val LOCALE_KEY = stringPreferencesKey("locale_setting")
        @Volatile
        private var INSTANCE: SettingPreferences? = null
        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences {
            return INSTANCE ?: synchronized(this){
                val instance = SettingPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}