package com.example.twinky.post

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.twinky.R
import com.example.twinky.databinding.ActivityItemChatBinding

class ItemChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityItemChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val name = intent.getStringExtra("NameChat")
        binding.namegroup.text = name

        val imageUrl = intent.getStringExtra("ImageChat")
        Glide.with(this).load(imageUrl).placeholder(R.drawable.frog).into(binding.groupImage)
    }


}