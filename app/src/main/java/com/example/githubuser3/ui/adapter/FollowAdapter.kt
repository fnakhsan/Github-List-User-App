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
import com.example.githubuser3.data.model.FollowModel

class FollowAdapter(private val listFollow: List<FollowModel>) :
    RecyclerView.Adapter<FollowAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: FollowModel)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_user, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listUser = listFollow[position]
        holder.apply {
            tvName.text = listUser.login
            Glide.with(itemView.context)
                .load(listUser.avatarUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(tvImage)
            itemView.setOnClickListener { onItemClickCallback.onItemClicked(listUser) }
        }
    }

    override fun getItemCount() = listFollow.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tv_item_name)
        val tvImage: ImageView = view.findViewById(R.id.img_item_photo)
    }
}