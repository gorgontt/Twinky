package com.example.twinky.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.twinky.Models.MovieModel
import com.example.twinky.Models.Post
import com.example.twinky.Models.User
import com.example.twinky.R
import com.example.twinky.databinding.FollowRvBinding
import com.example.twinky.databinding.PopularRvBinding
import com.squareup.picasso.Picasso
import io.reactivex.rxjava3.annotations.NonNull


internal class PopularRvAdapter(private var moviesList: List<MovieModel>) :
    RecyclerView.Adapter<PopularRvAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
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
    }
    override fun getItemCount(): Int {
        return moviesList.size
    }
}


/*class PopularRvAdapter (var context: Context, var popularList: ArrayList<Post>): RecyclerView.Adapter<PopularRvAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: PopularRvBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = PopularRvBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return popularList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val popular = popularList[position]

        holder.binding.name.text = popular.nameGroup
        Glide.with(context).load(popular.postUtl).placeholder(R.drawable.frog).into(holder.binding.image)

        // holder.binding.nameOfGroup.text = post.nameGroup
        Picasso.get().load(popular.postUtl).into(holder.binding.image)

    }
}

 */