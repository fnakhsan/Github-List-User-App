package com.example.githubuser2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
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
        holder.tvName.text = listUser.login
        Glide.with(holder.tvImage)
            .load(listUser.avatarUrl)
            .apply(RequestOptions().override(55,55))
            .into(holder.tvImage)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listUser) }
    }

    override fun getItemCount() = listUsers.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tv_item_name)
        val tvImage: ImageView = view.findViewById(R.id.img_item_photo)
    }
}

//class UserAdapter(private val listUsers: List<UserResponse>) :
//    RecyclerView.Adapter<UserAdapter.UsersViewHolder>() {
//
//    private lateinit var onItemClickCallback: OnItemClickCallback
//
//    interface OnItemClickCallback {
//        fun onItemClicked(data: UserResponse)
//    }
//
//    inner class UsersViewHolder(private val binding: ItemRowUserBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(user: UserResponse) {
//            binding.tvItemName.text = user.login
//            itemView.setOnClickListener { onItemClickCallback.onItemClicked(listUsers[adapterPosition]) }
//            Glide.with(binding.imgItemPhoto)
//                .load(user.avatarUrl)
//                .apply(RequestOptions().override(55, 55))
//                .into(binding.imgItemPhoto)
//        }
//    }
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.UsersViewHolder {
//        return UsersViewHolder(
//            ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        )
//    }
//
//    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) = holder.bind(
//        listUsers[position]
//
//    )
//
//    override fun getItemCount(): Int = listUsers.size
//
//}

//class UserAdapter(private val githubUsers: ArrayList<GithubUser>, private val clickListener: (String, View) -> Unit) : RecyclerView.Adapter<UserAdapter.UsersViewHolder>() {
//
//    inner class UsersViewHolder(private val binding: ItemUserListBinding) : RecyclerView.ViewHolder(binding.root){
//        fun bind(githubUser: GithubUser, click: (String, View) -> Unit) {
//            binding.data = githubUser
//            binding.root.transitionName = githubUser.login
//            binding.root.setOnClickListener { click(githubUser.login, binding.root) }
//        }
//    }
//
//    fun setData(items: List<GithubUser>){
//        githubUsers.apply {
//            clear()
//            addAll(items)
//        }
//        notifyDataSetChanged()
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
//        return UsersViewHolder(
//            ItemUserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        )
//    }
//
//    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) = holder.bind(githubUsers[position], clickListener)
//
//    override fun getItemCount(): Int = githubUsers.size
//}