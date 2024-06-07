package com.example.twinky

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.twinky.databinding.ActivityHomeBinding
import com.example.twinky.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {

    private lateinit var name: String

    private lateinit var binding: ActivityUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        name = getIntent().getStringExtra("NameUser").toString()
        binding.nameTvFragProfile.setText(name)

        val imageUrl = intent.getStringExtra("ImageUser")
        Glide.with(this).load(imageUrl).placeholder(R.drawable.frog).into(binding.profileImageFragProfile)


    }
}