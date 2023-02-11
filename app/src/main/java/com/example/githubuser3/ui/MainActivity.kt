package com.example.githubuser3.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.githubuser3.R
import com.example.githubuser3.databinding.ActivityMainBinding
import com.example.githubuser3.ui.favorite.FavoriteFragment
import com.example.githubuser3.ui.home.HomeFragment
import com.example.githubuser3.ui.setting.SettingFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_GithubUser3)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.bottom_nav_home -> setCurrentFragment(HomeFragment())
                R.id.bottom_nav_favorite -> setCurrentFragment(FavoriteFragment())
                R.id.bottom_nav_setting -> setCurrentFragment(SettingFragment())
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) = supportFragmentManager.beginTransaction().apply {
        replace(R.id.nav_host, fragment)
        commit()
    }
}