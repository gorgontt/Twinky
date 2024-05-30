package com.example.twinky

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.twinky.databinding.ActivityHomeBinding
import com.example.twinky.databinding.ActivitySettingsBinding
import com.example.twinky.fragments.ProfileFragment

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.profile.setOnClickListener {

            val intent = Intent(this@SettingsActivity, SignUpActivity::class.java)
            intent.putExtra("MODE", 1)
            startActivity(intent)
        }

        binding.addAccount.setOnClickListener {

            val intent = Intent(this@SettingsActivity, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.exitFromAccount.setOnClickListener {

            val intent = Intent(this@SettingsActivity, SignUpActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }

        binding.exitBtn.setOnClickListener {

            val intent = Intent(this@SettingsActivity, ProfileFragment::class.java)
            startActivity(intent)
            finishAffinity()
        }

    }
}