package com.example.twinky.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.twinky.Models.MovieModel
import com.example.twinky.Models.Post
import com.example.twinky.Models.User
import com.example.twinky.R
import com.example.twinky.databinding.FollowRvBinding
import com.example.twinky.databinding.PopularRvBinding
import com.example.twinky.post.ItemRecyclerActivity
import com.example.twinky.post.PopularItemRecyclerActivity
import com.squareup.picasso.Picasso
import io.reactivex.rxjava3.annotations.NonNull


internal class PopularRvAdapter(private var moviesList: List<MovieModel>, private val context: Context) :
    RecyclerView.Adapter<PopularRvAdapter.MyViewHolder>() {

    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val btn: ConstraintLayout = view.findViewById(R.id.open_btn)
        var title: TextView = view.findViewById(R.id.name_popular)
        var image: ImageView = view.findViewById(R.id.image_popular)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.popular_rv, parent, false)
        return MyViewHolder(itemView)

    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val movie = moviesList[position]
        holder.title.text = movie.getTitle()
        holder.image.setImageResource(moviesList.get(position).getImageResId())


        holder.btn.setOnClickListener {

            val intent = Intent(context, PopularItemRecyclerActivity::class.java)
            intent.putExtra("Title", moviesList.get(position).getTitle())
            intent.putExtra("Image", moviesList.get(position).getImageResId())
            context.startActivity(intent)
        }

        
    }
    override fun getItemCount(): Int {
        return moviesList.size
    }


}


