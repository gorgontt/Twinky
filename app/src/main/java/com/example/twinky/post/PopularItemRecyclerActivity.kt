package com.example.twinky.post

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.twinky.R
import com.example.twinky.databinding.ActivityItemRecyclerBinding
import com.example.twinky.databinding.ActivityPopularItemRecyclerBinding

class PopularItemRecyclerActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityPopularItemRecyclerBinding.inflate(layoutInflater)
    }


    private lateinit var name: String
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

        name = getIntent().getStringExtra("Title").toString()
        binding.nameGroup.setText(name)

        val imageResId = intent.getIntExtra("Image", 0)
        Glide.with(this).load(imageResId).into(binding.postImg)

        binding.like.setOnClickListener {
            if (isLiked) {
                binding.like.setImageResource(R.drawable.like)
            } else {
                binding.like.setImageResource(R.drawable.red_like)
            }
            isLiked = !isLiked
        }

        binding.send.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, imageResId)
            startActivity(Intent.createChooser(shareIntent, "Share post link"))
        }
    }
}