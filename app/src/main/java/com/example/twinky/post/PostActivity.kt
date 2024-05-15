package com.example.twinky.post

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.twinky.R
import com.example.twinky.databinding.ActivityPostBinding
import com.example.twinky.utils.POST_FOLDER
import com.example.twinky.utils.USER_PROFILE_FOLDER
import com.example.twinky.utils.uploadImage

class PostActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityPostBinding.inflate(layoutInflater)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()){ uri->
        uri?.let{
            uploadImage(uri, POST_FOLDER) {
                if (it != null) {

                    binding.selectImage.setImageURI(uri)
                }
            }
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.materialToolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        binding.materialToolbar.setNavigationOnClickListener {
            finish()
        }

        binding.selectImage.setOnClickListener {
            launcher.launch("image/*")
        }
    }
}