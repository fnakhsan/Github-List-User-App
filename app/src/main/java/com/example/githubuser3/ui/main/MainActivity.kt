package com.example.githubuser3.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.githubuser3.R
import com.example.githubuser3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_GithubUser3)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
    }
}