package com.example.githubuser3.ui.detail

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuser3.ui.adapter.SectionsPagerAdapter
import com.example.githubuser3.R
import com.example.githubuser3.data.model.UserModel
import com.example.githubuser3.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {
    private lateinit var detailBinding: ActivityDetailBinding
    private lateinit var username: String
    private lateinit var intentString: String
    private val detailViewModel by viewModels<DetailViewModel>()
    private val args: DetailActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

//        @Suppress("DEPRECATION")
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            window.insetsController?.hide(WindowInsets.Type.statusBars())
//        } else {
//            window.setFlags(
//                WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN
//            )
//        }
        username = intent?.getStringExtra(EXTRA_USERNAME) ?: args.username
        supportActionBar?.title = username
        Log.d(TAG, username)
        intentString = ""
        detailViewModel.apply {
            detailUser(username)
            detailResponse.observe(this@DetailActivity) { profile ->
                setUserData(profile)
//                intentString = getString(R.string.intent, profile.login, profile.htmlUrl, profile.name, profile.location, profile.company, profile.followers)
            }
            isLoading.observe(this@DetailActivity) {
                showLoading(it)
            }
        }
        setViewPager(username)
        supportActionBar?.elevation = 0f

        Log.d(TAG, "sampe oncret bisa")
    }

    private fun setUserData(profile: UserModel) {
        detailBinding.apply {
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
//        lifecycleScope.launch(Dispatchers.IO) {
//            val fav = menu?.findItem(R.id.action_favorite)
//            favStatus = detailViewModel.isFavorite(language.name)
//            when (favStatus) {
//                true -> {
//                    fav?.setIcon(R.drawable.ic_selected_favorite_24)
//                }
//                false -> {
//                    fav?.setIcon(R.drawable.ic_baseline_favorite_24)
//                }
//            }
//        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_share -> {
                Toast.makeText(
                    this,
                    "Shared $title",
                    Toast.LENGTH_SHORT
                ).show()
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, intentString)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }
//            R.id.action_favorite -> {
//                when (favStatus) {
//                    false -> {
//                        lifecycleScope.launch(Dispatchers.IO) {
//                            detailViewModel.insert(language)
//                        }
//                        item.setIcon(R.drawable.ic_selected_favorite_24)
//                        favStatus = true
//                        Toast.makeText(
//                            this,
//                            "Liked $title",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                    true -> {
//                        lifecycleScope.launch(Dispatchers.IO) {
//                            detailViewModel.delete(language)
//                        }
//                        item.setIcon(R.drawable.ic_baseline_favorite_24)
//                        favStatus = false
//                        Toast.makeText(
//                            this,
//                            "Disliked $title",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                }
//            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
        private const val EXTRA_USERNAME = "extra_username"
        private const val TAG = "detail"
    }
}