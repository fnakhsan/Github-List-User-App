package com.example.githubuser3.ui.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuser3.R
import com.example.githubuser3.data.Resource
import com.example.githubuser3.data.model.UserModel
import com.example.githubuser3.databinding.ActivityDetailBinding
import com.example.githubuser3.ui.adapter.SectionsPagerAdapter
import com.example.githubuser3.util.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {
    private lateinit var detailBinding: ActivityDetailBinding
    private lateinit var username: String
    private var login: String = ""
    private var url: String = ""
    private var name: String = ""

    //    private val detailViewModel by viewModels<DetailViewModel>()
//    private lateinit var factory: ViewModelFactory

    private val args: DetailActivityArgs by navArgs()

    private var favStatus: Boolean = false
    private val factory = ViewModelFactory.getInstance(this@DetailActivity)
    private val detailViewModel: DetailViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        username = intent?.getStringExtra(EXTRA_USERNAME) ?: args.username
        supportActionBar?.title = username
        Log.d(TAG, username)
        Log.d(TAG, "test")

        detailViewModel.apply {
            Log.d(TAG, "masuk view model")
            getDetail(username).observe(this@DetailActivity) {
                Log.d(TAG, "observe view model")
                when (it) {
                    is Resource.Success -> {
//                            lifecycleScope.launch(Dispatchers.IO) {
                        setUserData(it.data)
                        Log.d(TAG, it.toString())
//                            }
                        showLoading(false)
                    }
                    is Resource.Error -> {
                        Log.d(TAG, it.error)
                        showLoading(false)
                    }
                    is Resource.Loading -> showLoading(true)
                }
            }
//                if (isFavorite(username)) {
//                }
            Log.d(TAG, "akhir view model")

        }

        Log.d(TAG, "test123")
//        detailViewModel.apply {
//            detailUser(username)
//            detailResponse.observe(this@DetailActivity) { profile ->
//                setUserData(profile)
//                favorite.username = profile.login
//                favorite.avatar = profile.avatarUrl
//                Log.d(TAG, "detail vm observe")
//            }
//            isLoading.observe(this@DetailActivity) {
//                showLoading(it)
//            }
//        }
        setViewPager(username)
        supportActionBar?.elevation = 0f

        Log.d(TAG, "sampe oncret bisa")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Log.d(TAG, "onCreateMenu")
        menuInflater.inflate(R.menu.menu_detail, menu)

        val fav = menu?.findItem(R.id.action_favorite)
        lifecycleScope.launch(Dispatchers.IO) {
            when (detailViewModel.isFavorite(username)) {
                true -> {
                    fav?.setIcon(R.drawable.ic_favorite_selected)
                }
                false -> {
                    fav?.setIcon(R.drawable.ic_favorite_default)
                }
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_share -> {
                Toast.makeText(
                    this,
                    "Share ${supportActionBar?.title}",
                    Toast.LENGTH_SHORT
                ).show()
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    val stringIntent = getString(R.string.intent, login, url, name)
                    putExtra(Intent.EXTRA_TEXT, stringIntent)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }
            R.id.action_favorite -> {
                detailViewModel.apply {
                    lifecycleScope.launch(Dispatchers.IO) {
                        favStatus = isFavorite(username)
                    }
                    Log.d(TAG, "harusnya aman")
                    getDetail(username).observe(this@DetailActivity) {
                        when (it) {
                            is Resource.Success -> {
                                val user: UserModel = it.data
                                when (favStatus) {
                                    true -> {
                                        deleteFavUser(user)
                                        Log.d(TAG, "Success delete: ${it.data}")
                                        item.setIcon(R.drawable.ic_favorite_default)
                                        Toast.makeText(
                                            this@DetailActivity,
                                            "Disliked ${supportActionBar?.title}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        Log.d(TAG, "finish true")
                                    }
                                    false -> {
                                        setFavUser(user)
                                        Log.d(TAG, "Success insert: ${it.data}")
                                        item.setIcon(R.drawable.ic_favorite_selected)
                                        Toast.makeText(
                                            this@DetailActivity,
                                            "Liked ${supportActionBar?.title}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        Log.d(TAG, "finish false")
                                    }
                                }
                                showLoading(false)
                            }
                            is Resource.Error -> {
                                Log.d(TAG, it.error)
                                showLoading(false)
                            }
                            is Resource.Loading -> showLoading(true)
                        }
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUserData(profile: UserModel) {
        login = profile.login
        url = profile.htmlUrl
        name = profile.name
        detailBinding.apply {
            tvItemId.text = login
            tvItemUsername.text = name
            tvItemRepo.text = profile.repository.toString()
            tvItemFollower.text = profile.followers.toString()
            tvItemFollowing.text = profile.following.toString()
            tvItemLocation.text = profile.location
            tvItemCompany.text = profile.company
            tabLocation.visibility = if (profile.location == null) View.GONE else View.VISIBLE
            tabCompany.visibility = if (profile.company == null) View.GONE else View.VISIBLE
        }

        Glide.with(this@DetailActivity)
            .load(profile.avatar_url)
            .into(detailBinding.imgItemPhoto)
    }

    private fun showLoading(it: Boolean) {
        detailBinding.progressBar2.visibility = if (it) View.VISIBLE else View.GONE
    }

    private fun setViewPager(username: String) {
        Log.d(TAG, "setViewPager")
        val sectionsPagerAdapter = SectionsPagerAdapter(this, username)
        val viewPager: ViewPager2 = detailBinding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = detailBinding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

//    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel {
//        val factory = ViewModelFactory.getInstance(activity.application)
//        return ViewModelProvider(activity, factory)[DetailViewModel::class.java]
//    }

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