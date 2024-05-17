package com.example.twinky.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.twinky.Models.Post
import com.example.twinky.Models.Reel
import com.example.twinky.Models.User
import com.example.twinky.R
import com.example.twinky.databinding.MyGroupsItemDesignBinding
import com.example.twinky.databinding.PostRvBinding
import com.example.twinky.utils.USER_NODE
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class PostAdapter (var context: Context, var postList: ArrayList<Post>): RecyclerView.Adapter<PostAdapter.MyHolder>() {

    inner class MyHolder(var binding: PostRvBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        var binding = PostRvBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {

        try {
            Firebase.firestore.collection(USER_NODE).document(postList.get(position).uid).get().addOnSuccessListener {

                var user = it.toObject<User>()
                Glide.with(context).load(user!!.image).placeholder(R.drawable.profile).into(holder.binding.profileImage)
                holder.binding.nameTvFragProfile.text = user.userName
            }
        }catch (e: Exception){

        }

        Glide.with(context).load(postList.get(position).postUtl).placeholder(R.drawable.loading).into(holder.binding.postImg)
        holder.binding.time.text = postList.get(position).time
        holder.binding.caption.text = postList.get(position).caption

    }
}