package com.example.githubuser3.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuser3.R
import com.example.githubuser3.data.model.UserModel

class UserAdapter(private val listUsers: List<UserModel>) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: UserModel)
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
            Glide.with(itemView.context)
                .load(listUser.avatar_url)
                .apply(RequestOptions.circleCropTransform())
                .into(tvImage)
            itemView.setOnClickListener { onItemClickCallback.onItemClicked(listUser) }
        }
    }

    override fun getItemCount() = listUsers.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tv_item_name)
        val tvImage: ImageView = view.findViewById(R.id.img_item_photo)
    }
}