package com.example.twinky

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.twinky.fragments.ChatFragment

class ChatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val nameChat = intent.getStringExtra("name_chat")
        val imageUrl = intent.getStringExtra("image_url")

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container_jj, ChatFragment.newInstance(nameChat!!, imageUrl!!))
                .commit()
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container_jj, ChatFragment.newInstance(nameChat, imageUrl))
                .commit()
        }
    }
}