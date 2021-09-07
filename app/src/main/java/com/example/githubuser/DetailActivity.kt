package com.example.githubuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import com.example.githubuser.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    companion object {
        val EXTRA_USER = "extra_user"
    }

    private var title: String = "Profile"

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setActionBar()


        val user = intent.getParcelableExtra<User>(EXTRA_USER)

        binding.imgItemPhoto.setImageResource(user?.avatar!!)
        binding.tvItemUsername.text = user.username
        binding.tvItemName.text = user.name
        binding.tvItemLocation.text = user.location
        binding.tvItemCompany.text = user.company
        binding.tvItemRepo.text = user.repository
        binding.tvItemFollower.text = user.follower
        binding.tvItemFollowing.text = user.following

    }

    private fun setActionBar() {
        supportActionBar?.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_share, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        val kirim = intent.getParcelableExtra<User>(EXTRA_USER)

        if (id == R.id.action_share) {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT,
                    "${kirim?.name} (${kirim?.username}), bertempat tinggal di ${kirim?.location}.\n" +
                            "Bekerja di ${kirim?.company}.\n" +
                            "${kirim?.repository} Repository, ${kirim?.follower} follower, ${kirim?.following} following.")
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        if (id == R.id.home){
            onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }

}