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
import com.example.twinky.databinding.PostRvBinding
import com.example.twinky.post.ItemRecyclerActivity
import com.example.twinky.utils.USER_NODE
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class PostAdapter (var context: Context, var postList: ArrayList<Post>) : RecyclerView.Adapter<PostAdapter.MyHolder>() {


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

        try {
            val text = TimeAgo.using(postList.get(position).time.toLong())
            holder.binding.time.text = text
        }catch (e: Exception){
            holder.binding.time.text = ""
        }

        holder.binding.send.setOnClickListener {
            var i = Intent(Intent.ACTION_SEND)
            i.type = "text/plain"
            i.putExtra(Intent.EXTRA_TEXT, postList.get(position).postUtl)
            context.startActivity(i)
        }
        //holder.binding.time.text = postList.get(position).time
        holder.binding.nameGroup.text = postList.get(position).nameGroup
        holder.binding.like.setOnClickListener {

            holder.binding.like.setImageResource(R.drawable.red_like)

        }

        holder.binding.favourites.setOnClickListener {

            holder.binding.favourites.setImageResource(R.drawable.bookmark)

        }


        holder.binding.postImg.setOnClickListener {

            val intent = Intent(context, ItemRecyclerActivity::class.java)
            intent.putExtra("NamePost", postList.get(position).nameGroup)
            intent.putExtra("ImagePost", postList.get(position).postUtl)
            intent.putExtra("CaptionPost", postList.get(position).caption)
            intent.putExtra("Time", postList.get(position).time.toLong())
            context.startActivity(intent)
        }

    }
}