package com.example.twinky

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.twinky.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
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
                        Toast.makeText(this@SignUpActivity, "Успешный вход", Toast.LENGTH_SHORT).show()
                    } else
                    {
                        Toast.makeText(this@SignUpActivity, result.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}