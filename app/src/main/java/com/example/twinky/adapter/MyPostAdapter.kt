package com.example.twinky.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.twinky.Models.Post
import com.example.twinky.Models.User
import com.example.twinky.R
import com.example.twinky.databinding.MyGroupsItemDesignBinding
import com.example.twinky.utils.USER_NODE
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
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

        val post = postList[position]

        holder.binding.nameOfGroup.text = post.nameGroup
        Glide.with(context).load(post.postUtl).placeholder(R.drawable.loading).into(holder.binding.postImage)

        // Remove the redundant line below as you have already set the nameOfGroup above
        // holder.binding.nameOfGroup.text = post.nameGroup
        Picasso.get().load(post.postUtl).into(holder.binding.postImage)

        /*try {
            Firebase.firestore.collection(USER_NODE).document(postList.get(position).uid).get().addOnSuccessListener {

                var post = it.toObject<Post>()
                holder.binding.nameOfGroup.text = post?.nameGroup
            }
        }catch (e: Exception){

        }

        Glide.with(context).load(postList.get(position).postUtl).placeholder(R.drawable.loading).into(holder.binding.postImage)



        //holder.binding.time.text = postList.get(position).time
        holder.binding.nameOfGroup.text = postList.get(position).nameGroup

        Picasso.get().load(postList.get(position).postUtl).into(holder.binding.postImage)

         */
    }
}