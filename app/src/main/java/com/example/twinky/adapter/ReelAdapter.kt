package com.example.twinky.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.twinky.Models.Post
import com.example.twinky.Models.Reel
import com.example.twinky.databinding.MyGroupsItemDesignBinding
import com.squareup.picasso.Picasso

class ReelAdapter (var context: Context, var reelList: ArrayList<Reel>): RecyclerView.Adapter<ReelAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: MyGroupsItemDesignBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = MyGroupsItemDesignBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return reelList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(reelList.get(position).reelUtl).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.binding.postImage)
    }
}