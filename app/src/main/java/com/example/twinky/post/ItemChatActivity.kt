package com.example.twinky.post

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
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

        binding.sendMessage.setOnClickListener {
            sendMessage()
        }

        binding.editTextText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                if (s.isNullOrEmpty()) {
                    binding.sendMessage.setImageResource(R.drawable.send)
                } else {
                    binding.sendMessage.setImageResource(R.drawable.send2)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun sendMessage() {
        val message = binding.editTextText.text.toString()
        if (message.isNotEmpty()) {
            binding.tvMessage.text = message
            binding.constraint.visibility = View.VISIBLE
            binding.editTextText.text.clear()
        }
    }
}

