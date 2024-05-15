package com.example.twinky

import android.content.Intent
import android.os.Bundle
import android.text.Html
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
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class SignUpActivity : AppCompatActivity() {

    val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    lateinit var user: User
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()){uri->
        uri?.let{
            uploadImage(uri, USER_PROFILE_FOLDER) {
                if (it != null) {

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

        val text = "<font color=#495551>Уже есть аккаунт? </font> <font color=#BEEF62>Вход</font>"
        binding.login.setText(Html.fromHtml(text))

        user = User()

        if (intent.hasExtra("MODE")){
            if (intent.getIntExtra("MODE", -1)==1){
                binding.signUpBtn.text = "Редактировать"
                Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
                    .addOnSuccessListener {

                        user = it.toObject<User>()!!

                        if (!user.image.isNullOrEmpty()){
                            Picasso.get().load(user.image).into(binding.profileImage)
                        }

                        binding.editTName.editText?.setText(user.userName)
                        binding.edTEmail.editText?.setText(user.email)
                        binding.editTPassword.editText?.setText(user.password)
                    }
            }
        }

        binding.signUpBtn.setOnClickListener{

            if (intent.hasExtra("MODE")){
                if (intent.getIntExtra("MODE", -1)==1){

                    Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).set(user)
                        .addOnSuccessListener {

                            startActivity(Intent(this@SignUpActivity, HomeActivity::class.java))
                            finish()
                        }

                }

            }else{

            }


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

                                startActivity(Intent(this@SignUpActivity, HomeActivity::class.java))
                                finish()
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
        binding.login.setOnClickListener {
            startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
            finish()
        }
    }
}