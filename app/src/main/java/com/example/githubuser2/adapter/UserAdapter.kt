package com.example.githubuser2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuser2.R
import com.example.githubuser2.data.UserResponse

class UserAdapter(private val listUsers: List<UserResponse>) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: UserResponse)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_user, viewGroup, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listUser = listUsers[position]
        holder.apply {
            tvName.text = listUser.login
            Glide.with(tvImage)
                .load(listUser.avatarUrl)
                .apply(RequestOptions().override(55, 55))
                .into(tvImage)
            itemView.setOnClickListener { onItemClickCallback.onItemClicked(listUser) }
        }
    }

    override fun getItemCount() = listUsers.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tv_item_name)
        val tvImage: ImageView = view.findViewById(R.id.img_item_photo)
    }
}