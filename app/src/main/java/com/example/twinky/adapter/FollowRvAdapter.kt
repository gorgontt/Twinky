package com.example.twinky.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.twinky.Models.User
import com.example.twinky.R
import com.example.twinky.UserActivity
import com.example.twinky.databinding.FollowRvBinding
import com.example.twinky.databinding.SearchRvBinding
import com.example.twinky.post.ItemRecyclerActivity

class FollowRvAdapter (var context: Context, var followList: ArrayList<User>): RecyclerView.Adapter<FollowRvAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: FollowRvBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = FollowRvBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return followList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(followList.get(position).image).placeholder(R.drawable.frog).into(holder.binding.userImage)
        holder.binding.name.text = followList.get(position).userName

        holder.binding.userImage.setOnClickListener {

            val intent = Intent(context, UserActivity::class.java)
            intent.putExtra("NameUser", followList.get(position).userName)
            intent.putExtra("ImageUser", followList.get(position).image)
            context.startActivity(intent)
        }
    }


}
