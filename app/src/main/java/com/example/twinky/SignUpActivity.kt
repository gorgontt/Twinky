package com.example.twinky

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.twinky.Models.User
import com.example.twinky.databinding.ActivitySignUpBinding
import com.example.twinky.utils.USER_NODE
import com.example.twinky.utils.USER_PROFILE_FOLDER
import com.example.twinky.utils.uploadImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    lateinit var user: User
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()){uri->
        uri?.let{
            uploadImage(uri, USER_PROFILE_FOLDER) {
                if (it == null) {

                }else {
                    user.image = it
                    binding.profileImage.setImageURI(uri)
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

        user = User()

        binding.signUpBtn.setOnClickListener{

            if (binding.editTName.editText?.text.toString().equals("") or
                binding.edTEmail.editText?.text.toString().equals("") or
                binding.editTPassword.editText?.text.toString().equals(""))
            {
                Toast.makeText(this@SignUpActivity, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show()

            } else
            {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.edTEmail.editText?.text.toString(),
                    binding.editTPassword.editText?.text.toString()
                ).addOnCompleteListener {
                    result->

                    if (result.isSuccessful){
                        user.userName = binding.editTName.editText?.text.toString()
                        user.password = binding.editTPassword.editText?.text.toString()
                        user.email = binding.edTEmail.editText?.text.toString()

                        Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).set(user)
                            .addOnSuccessListener {
                                Toast.makeText(this@SignUpActivity, "Login", Toast.LENGTH_SHORT).show()
                            }
                    } else
                    {
                        Toast.makeText(this@SignUpActivity, result.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.addImageSignUp.setOnClickListener {
            launcher.launch("image/*")
        }
    }
}