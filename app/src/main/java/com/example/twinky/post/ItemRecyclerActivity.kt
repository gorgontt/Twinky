package com.example.twinky.post

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.twinky.Models.Post
import com.example.twinky.R
import com.example.twinky.adapter.ReelAdapter
import com.example.twinky.databinding.ActivityItemRecyclerBinding
import com.github.marlonlom.utilities.timeago.TimeAgo


class ItemRecyclerActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityItemRecyclerBinding.inflate(layoutInflater)
    }


    private lateinit var name: String
    private lateinit var caption: String
    private var time: Long = 0
    private var isLiked = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        name = getIntent().getStringExtra("NamePost").toString()
        binding.nameGroup.setText(name)

        caption = getIntent().getStringExtra("CaptionPost").toString()
        binding.captionGroup.setText(caption)


        time = intent.getLongExtra("Time", 0)
        val text = TimeAgo.using(time)
        binding.timeItem.setText(text)


        val imageUrl = intent.getStringExtra("ImagePost")
        Glide.with(this).load(imageUrl).placeholder(R.drawable.frog).into(binding.postImg)

        binding.like.setOnClickListener {
            if (isLiked) {
                binding.like.setImageResource(R.drawable.like)
            } else {
                binding.like.setImageResource(R.drawable.red_like)
            }
            isLiked = !isLiked
        }

        binding.favourites.setOnClickListener {
            binding.favourites.setImageResource(R.drawable.bookmark)
        }

        binding.send.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, imageUrl)
            startActivity(Intent.createChooser(shareIntent, "Share post link"))
        }

        binding.joinGroup.setOnClickListener {
            val intent = Intent(this, ItemChatActivity::class.java)
            intent.putExtra("NameChat", name)
            intent.putExtra("ImageChat", imageUrl)
            startActivity(intent)
        }

    }
}

