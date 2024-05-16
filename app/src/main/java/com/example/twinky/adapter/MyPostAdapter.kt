package com.example.twinky.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.twinky.Models.Post
import com.example.twinky.databinding.MyGroupsItemDesignBinding
import com.squareup.picasso.Picasso

class MyPostAdapter (var context: Context, var postList: ArrayList<Post>): RecyclerView.Adapter<MyPostAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: MyGroupsItemDesignBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = MyGroupsItemDesignBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(postList.get(position).postUtl).into(holder.binding.postImage)
    }
}