package com.example.githubuser2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuser2.data.FollowResponseItem

class FollowAdapter(private val listFollow: List<FollowResponseItem>) :
    RecyclerView.Adapter<FollowAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_user, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listUser = listFollow[position]
        holder.tvName.text = listUser.login
        Glide.with(holder.tvImage)
            .load(listUser.avatarUrl)
            .apply(RequestOptions().override(55, 55))
            .into(holder.tvImage)
    }

    override fun getItemCount() = listFollow.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tv_item_name)
        val tvImage: ImageView = view.findViewById(R.id.img_item_photo)
    }
}