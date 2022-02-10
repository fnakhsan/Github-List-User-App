package com.example.githubuser2

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.navigation.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuser2.adapter.SectionsPagerAdapter
import com.example.githubuser2.data.UserResponse
import com.example.githubuser2.databinding.ActivityDetailBinding
import com.example.githubuser2.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var detailBinding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()
    private val args: DetailActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        detailViewModel.detailUser(args.username)

        detailViewModel.detailResponse.observe(this) { profile ->
            setUserData(profile)
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        setViewPager(args.username)
        supportActionBar?.elevation = 0f
    }

    private fun setUserData(profile: UserResponse) {
        with(detailBinding) {
            tvItemId.text = profile.login
            tvItemUsername.text = profile.name
            tvItemRepo.text = profile.repository.toString()
            tvItemFollower.text = profile.followers.toString()
            tvItemFollowing.text = profile.following.toString()
            tvItemLocation.text = profile.location?.toString()
            tvItemCompany.text = profile.company?.toString()
            tabLocation.visibility = if (profile.location == null) View.GONE else View.VISIBLE
            tabCompany.visibility = if (profile.company == null) View.GONE else View.VISIBLE
        }
        Glide.with(this@DetailActivity)
            .load(profile.avatarUrl)
            .into(detailBinding.imgItemPhoto)
    }

    private fun showLoading(it: Boolean) {
        detailBinding.progressBar2.visibility = if (it) View.VISIBLE else View.GONE
    }

    private fun setViewPager(username: String) {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, username)
        val viewPager: ViewPager2 = detailBinding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = detailBinding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
        private const val TAG = "DetailActivity"
    }

}