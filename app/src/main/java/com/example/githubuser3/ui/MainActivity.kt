package com.example.githubuser3.ui

import android.app.LocaleManager
import android.app.UiModeManager
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.githubuser3.R
import com.example.githubuser3.data.datastore.SettingPreferences
import com.example.githubuser3.databinding.ActivityMainBinding
import com.example.githubuser3.ui.setting.SettingViewModel
import com.example.githubuser3.ui.setting.dataStore
import com.example.githubuser3.util.SettingFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        darkMode()
        setTheme(R.style.Theme_GithubUser3)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.bottomNav.itemIconTintList = null
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController
        setupWithNavController(binding.bottomNav, navController)

    }

    private fun darkMode() {
        val preferences = SettingPreferences.getInstance(dataStore)
        val settingViewModel =
            ViewModelProvider(this, SettingFactory(preferences))[SettingViewModel::class.java]
        settingViewModel.getLocaleSetting().observe(this) {
            if (it == "in") {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    baseContext.getSystemService(LocaleManager::class.java).applicationLocales =
                        LocaleList.forLanguageTags("in")
                } else {
                    AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("in"))
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    baseContext.getSystemService(LocaleManager::class.java).applicationLocales =
                        LocaleList.forLanguageTags("en")
                } else {
                    AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("en"))
                }
            }
        }
        settingViewModel.getThemeSetting().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    UiModeManager.MODE_NIGHT_YES
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    UiModeManager.MODE_NIGHT_NO
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }
    }
}