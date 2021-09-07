package com.example.githubuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeActivity : AppCompatActivity() {
    private lateinit var rvUser : RecyclerView
    private var list: ArrayList<User> = arrayListOf()
    private var judul: String = "List User"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        rvUser =findViewById(R.id.rv_user)
        rvUser.setHasFixedSize(true)

        list.addAll(UsersData.listData)
        showRecyclerList()

        setActionBarTitle()
    }

    private fun setActionBarTitle() {
        supportActionBar?.title = judul
    }

    private fun showRecyclerList() {
        rvUser.layoutManager = LinearLayoutManager(this)
        val listUserAdapter = ListUserAdapter(list)
        rvUser.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(user: User) {
        Toast.makeText(this, "Kamu memilih " + user.name, Toast.LENGTH_SHORT).show()

        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("extra_user", user)
        startActivity(intent)
    }

}