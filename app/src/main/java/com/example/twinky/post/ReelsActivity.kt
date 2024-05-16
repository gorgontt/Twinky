package com.example.twinky.post

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.twinky.HomeActivity
import com.example.twinky.Models.Reel
import com.example.twinky.Models.User
import com.example.twinky.R
import com.example.twinky.databinding.ActivityReelsBinding
import com.example.twinky.utils.POST_FOLDER
import com.example.twinky.utils.REEL
import com.example.twinky.utils.REEL_FOLDER
import com.example.twinky.utils.USER_NODE
import com.example.twinky.utils.uploadImage
import com.example.twinky.utils.uploadVideo
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class ReelsActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityReelsBinding.inflate(layoutInflater)
    }
    lateinit var progressDialog: ProgressDialog
    private lateinit var videoUrl: String
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()){ uri->
        uri?.let{
            uploadVideo(uri, REEL_FOLDER, progressDialog) { url->

                if (url != null) {
                    videoUrl = url
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

        progressDialog = ProgressDialog(this)

        binding.selectReel.setOnClickListener {
            launcher.launch("video/*")
        }

        binding.exitBtn.setOnClickListener {
            startActivity(Intent(this@ReelsActivity, HomeActivity::class.java))
            finish()
        }

        binding.createBtn.setOnClickListener {
            Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
                var user: User = it.toObject<User>()!!

                val reel: Reel = Reel(videoUrl!!, binding.caption.editText?.text.toString(), user.image!!)

                Firebase.firestore.collection(REEL).document().set(reel).addOnSuccessListener {
                    Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + REEL).document().set(reel).addOnSuccessListener {
                        startActivity(Intent(this@ReelsActivity, HomeActivity::class.java))
                        finish()
                    }
                }
            }

        }
    }
}