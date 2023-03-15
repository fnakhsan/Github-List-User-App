package com.example.githubuser3.ui.detail

import android.content.Intent
import android.os.Bundle
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
import com.bumptech.glide.request.RequestOptions
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
    private var name: String? = ""

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

        detailViewModel.apply {
            getDetail(username).observe(this@DetailActivity) {
                when (it) {
                    is Resource.Success -> {
                        setUserData(it.data)
                        showLoading(false)
                    }
                    is Resource.Error -> {
                        showLoading(false)
                    }
                    is Resource.Loading -> showLoading(true)
                }
            }
        }
        setViewPager(username)
        supportActionBar?.elevation = 0f
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
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
                    putExtra(Intent.EXTRA_TEXT, getString(R.string.intent, login, url, name))
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
                    getDetail(username).observe(this@DetailActivity) {
                        when (it) {
                            is Resource.Success -> {
                                val user: UserModel = it.data
                                when (favStatus) {
                                    true -> {
                                        deleteFavUser(user)
                                        item.setIcon(R.drawable.ic_favorite_default)
                                        Toast.makeText(
                                            this@DetailActivity,
                                            "Disliked ${supportActionBar?.title}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    false -> {
                                        setFavUser(user)
                                        item.setIcon(R.drawable.ic_favorite_selected)
                                        Toast.makeText(
                                            this@DetailActivity,
                                            "Liked ${supportActionBar?.title}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                                showLoading(false)
                            }
                            is Resource.Error -> {
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
            tvItemUsername.text = name ?: ""
            tvItemRepo.text = profile.repository.toString()
            tvItemFollower.text = profile.followers.toString()
            tvItemFollowing.text = profile.following.toString()
            tvItemLocation.text = profile.location
            tvItemCompany.text = profile.company
            tvItemUsername.visibility = if (name == null) View.GONE else View.VISIBLE
            tabLocation.visibility = if (profile.location == null) View.GONE else View.VISIBLE
            tabCompany.visibility = if (profile.company == null) View.GONE else View.VISIBLE
        }

        Glide.with(this@DetailActivity)
            .load(profile.avatar_url)
            .apply(RequestOptions.circleCropTransform())
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
        private const val EXTRA_USERNAME = "extra_username"
    }
}