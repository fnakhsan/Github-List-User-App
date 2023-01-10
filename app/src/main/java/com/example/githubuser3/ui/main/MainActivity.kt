package com.example.githubuser3.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.githubuser3.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_GithubUser3)
        setContentView(R.layout.activity_main)
    }
}