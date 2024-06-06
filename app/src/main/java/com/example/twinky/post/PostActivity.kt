package com.example.twinky.post

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.twinky.HomeActivity
import com.example.twinky.Models.Post
import com.example.twinky.Models.User
import com.example.twinky.R
import com.example.twinky.databinding.ActivityPostBinding
import com.example.twinky.fragments.ChatFragment
import com.example.twinky.utils.POST
import com.example.twinky.utils.POST_FOLDER
import com.example.twinky.utils.USER_NODE
import com.example.twinky.utils.uploadImage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class PostActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityPostBinding.inflate(layoutInflater)
    }

    var imageUrl: String? = null

    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()){ uri->
        uri?.let{
            uploadImage(uri, POST_FOLDER) {
                url->

                if (url != null) {
                    binding.selectImage.setImageURI(uri)
                    imageUrl = url
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
            startActivity(Intent(this@PostActivity, HomeActivity::class.java))
            finish()
        }

        binding.selectImage.setOnClickListener {
            launcher.launch("image/*")
        }

        binding.exitBtn.setOnClickListener {
            startActivity(Intent(this@PostActivity, HomeActivity::class.java))
            finish()
        }


        binding.createBtn.setOnClickListener {
            val caption = binding.caption.text.toString()
            val nameGroup = binding.nameOfGroupEdT.text.toString()
            if (caption.isNotEmpty() && imageUrl != null) {
                val post = Post(
                    postUtl = imageUrl!!,
                    caption = caption,
                    nameGroup = nameGroup,
                    uid = Firebase.auth.currentUser!!.uid,
                    time = System.currentTimeMillis().toString()
                )

                Firebase.firestore.collection(POST).document().set(post).addOnSuccessListener {
                    Firebase.firestore.collection(Firebase.auth.currentUser!!.uid).document().set(post).addOnSuccessListener {
                        startActivity(Intent(this@PostActivity, HomeActivity::class.java))
                        finish()
                    }.addOnFailureListener { e ->
                        // Обработка ошибки
                        Log.e("PostActivity", "Error setting document", e)
                    }
                }.addOnFailureListener { e ->
                    // Обработка ошибки
                    Log.e("PostActivity", "Error setting document", e)
                }

                startActivity(createIntentWithData())
                finish()

            } else {
                // Обработка случая, когда caption пустое или imageUrl не установлен
                Log.e("PostActivity", "Caption is empty or imageUrl is not set")
            }
        }

        /*binding.createBtn.setOnClickListener {

            Firebase.firestore.collection(USER_NODE).document().get().addOnSuccessListener {

                var user = it.toObject<User>()!!
                val post: Post = Post(
                    postUtl = imageUrl!!,
                    caption = binding.caption.editText?.text.toString(),
                    uid = Firebase.auth.currentUser!!.uid,
                    time = System.currentTimeMillis().toString()
                )

                Firebase.firestore.collection(POST).document().set(post).addOnSuccessListener {
                    Firebase.firestore.collection(Firebase.auth.currentUser!!.uid).document().set(post).addOnSuccessListener {
                        startActivity(Intent(this@PostActivity, HomeActivity::class.java))
                        finish()
                    }
                }

            }


        }

         */
    }

    private fun createIntentWithData(): Intent {
        val intent = Intent(this, ChatFragment::class.java)
        intent.putExtra("name_chat", binding.nameOfGroupEdT.text.toString())
        intent.putExtra("image_url", imageUrl)
        return intent
    }
}