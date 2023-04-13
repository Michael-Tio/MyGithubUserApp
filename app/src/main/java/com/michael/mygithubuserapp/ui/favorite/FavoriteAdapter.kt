package com.michael.mygithubuserapp.ui.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.michael.mygithubuserapp.database.FavoriteUser
import com.michael.mygithubuserapp.databinding.ItemUsersBinding
import com.michael.mygithubuserapp.ui.detail.DetailUserActivity

class FavoriteAdapter (private val listUser: List<FavoriteUser>) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FavoriteAdapter.ViewHolder {
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvUsername.text = listUser[position].username
        Glide.with(viewHolder.itemView.context)
            .load(listUser[position].avatarUrl)
            .into(viewHolder.imgUser)

        viewHolder.itemView.setOnClickListener{
            val intent = Intent(viewHolder.itemView.context, DetailUserActivity::class.java)
            intent.putExtra(DetailUserActivity.USERNAME, listUser[position].username)
            viewHolder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    inner class ViewHolder(binding: ItemUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvUsername: TextView = binding.tvUsername
        val imgUser: ImageView = binding.imgUser
    }
}