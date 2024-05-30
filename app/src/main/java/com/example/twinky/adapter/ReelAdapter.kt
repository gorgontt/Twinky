package com.example.twinky.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.twinky.Models.Post
import com.example.twinky.Models.Reel
import com.example.twinky.Models.User
import com.example.twinky.R
import com.example.twinky.databinding.ChatRvBinding
import com.example.twinky.databinding.PostRvBinding
import com.example.twinky.databinding.ReelDgBinding
import com.example.twinky.post.ItemRecyclerActivity
import com.example.twinky.utils.USER_NODE
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class ReelAdapter (var context: Context, var postList: ArrayList<Post>): RecyclerView.Adapter<ReelAdapter.MyHolder>() {

    inner class MyHolder(var binding: ChatRvBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        var binding = ChatRvBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {

        Glide.with(context).load(postList.get(position).postUtl).placeholder(R.drawable.loading).into(holder.binding.chatImage)

        holder.binding.tvNameOfChat.text = postList.get(position).nameGroup


    }
}