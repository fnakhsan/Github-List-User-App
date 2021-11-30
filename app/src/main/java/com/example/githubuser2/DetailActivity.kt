package com.example.githubuser2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.navigation.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuser2.data.UserResponse
import com.example.githubuser2.databinding.ActivityDetailBinding
import com.example.githubuser2.fragment.FollowFragment
import com.example.githubuser2.viewmodel.DetailViewModel
import com.example.githubuser2.viewmodel.FollowViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var detailBinding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()
    private val args: DetailActivityArgs by navArgs()

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        detailViewModel.detailUser(args.username)

        detailViewModel.detailResponse.observe(this) { profile ->
            setUserData(profile)
        }

        detailViewModel.isLoading.observe(this, {
            showLoading(it)
        })

        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = SectionsPagerAdapter(this, args.username)
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    private fun setUserData(profile: UserResponse) {
        detailBinding.tvItemUsername.text = profile.name
        detailBinding.tvItemRepo.text = profile.repository.toString()
        detailBinding.tvItemFollower.text = profile.followers.toString()
        detailBinding.tvItemFollowing.text = profile.following.toString()
        Glide.with(this@DetailActivity)
            .load(profile.avatarUrl)
            .into(detailBinding.imgItemPhoto)
    }

    private fun showLoading(it: Boolean) {
        detailBinding.progressBar2.visibility = if (it) View.VISIBLE else View.GONE
    }

}