package com.example.twinky.ui.profile

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.twinky.R
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RegistrActivity : AppCompatActivity() {

    private var registerName: EditText? = null
    private var registerPhoneInput: EditText? = null
    private var registerPasswordInput: EditText? = null
    private var loadingBar: ProgressDialog? = null
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        registerButton = findViewById(R.id.register_btn)
        registerName = findViewById(R.id.registr_userName_input)
        registerPhoneInput = findViewById(R.id.registr_phone_input)
        registerPasswordInput = findViewById(R.id.registr_password_input)
        registerButton.setOnClickListener{ CreateAccount() }
        loadingBar = ProgressDialog(this)
    }

    private fun CreateAccount() {
        val username = registerName!!.getText().toString()
        val phone = registerPhoneInput!!.getText().toString()
        val password = registerPasswordInput!!.getText().toString()
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Enter name", Toast.LENGTH_SHORT).show()
            return
        }
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Enter phone", Toast.LENGTH_SHORT).show()
            return
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show()
            return
        }
        loadingBar!!.setTitle("Create Account")
        loadingBar!!.setMessage("Please wait")
        loadingBar!!.setCanceledOnTouchOutside(false)
        loadingBar!!.show()
        ValidatePhone(username, phone, password)
    }

    private fun ValidatePhone(username: String, phone: String, password: String) {

        val RootRef: DatabaseReference
        RootRef = FirebaseDatabase.getInstance().getReference()
        RootRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!snapshot.child("Users").child(phone).exists()) {
                    val userDataMap = HashMap<String, Any>()
                    userDataMap["phone"] = phone
                    userDataMap["name"] = username
                    userDataMap["password"] = password
                    RootRef.child("Users").child(phone).updateChildren(userDataMap)
                        .addOnCompleteListener { task: Task<Void?> ->
                            if (task.isSuccessful) {
                                loadingBar!!.dismiss()
                                Toast.makeText(
                                    this@RegistrActivity,
                                    "Успешная регистрация",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val logintIntent =
                                    Intent(this@RegistrActivity, LoginActivity::class.java)
                                startActivity(logintIntent)
                            } else {
                                loadingBar!!.dismiss()
                                Toast.makeText(
                                    this@RegistrActivity,
                                    "Не удалось зарегестрироваться",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                } else {
                    loadingBar!!.dismiss()
                    Toast.makeText(
                        this@RegistrActivity,
                        "Phone number already exists",
                        Toast.LENGTH_LONG
                    ).show()
                    val logintIntent = Intent(this@RegistrActivity, LoginActivity::class.java)
                    startActivity(logintIntent)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}


