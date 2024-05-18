package com.example.twinky.post

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.twinky.R
import com.example.twinky.databinding.ActivityItemRecyclerBinding


class ItemRecyclerActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityItemRecyclerBinding.inflate(layoutInflater)
    }


    private lateinit var name: String
    private lateinit var caption: String


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

        val imageUrl = intent.getStringExtra("ImagePost")
        Glide.with(this).load(imageUrl).placeholder(R.drawable.frog).into(binding.postImg)

    }
}


