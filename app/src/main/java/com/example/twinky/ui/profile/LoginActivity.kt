package com.example.twinky.ui.profile

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.twinky.Model.Users
import com.example.twinky.Prevalent.Prevalent
import com.example.twinky.R
import com.example.twinky.ui.home.HomeFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.rey.material.widget.CheckBox
import io.paperdb.Paper


class LoginActivity : AppCompatActivity() {
    private lateinit var loginButton: Button
    private lateinit var loginPhoneInput: EditText
    private lateinit var loginPasswordInput: EditText
    private lateinit var loadingBar: ProgressDialog
    private var parentDbName = "Users"
    private lateinit var rememberMe: CheckBox
    private lateinit var AdminLink: TextView
    private lateinit var notAdminLink: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { v: View, insets: WindowInsetsCompat ->
            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        loginButton = findViewById(R.id.login_btn)
        loginPhoneInput = findViewById(R.id.login_phone_input)
        loginPasswordInput = findViewById(R.id.login_password_input)
        loadingBar = ProgressDialog(this)
        rememberMe = findViewById(R.id.login_check_box)
        AdminLink = findViewById(R.id.admin_link)
        notAdminLink = findViewById(R.id.user_link)
        Paper.init(this)

        loginButton.setOnClickListener{
            loginUser()
        }

        AdminLink.setOnClickListener {
            AdminLink.visibility = View.INVISIBLE
            notAdminLink.visibility = View.VISIBLE
            loginButton.text = "Login Admin"
            parentDbName = "Admins"
        }

        notAdminLink.setOnClickListener{
            AdminLink.visibility = View.VISIBLE
            notAdminLink.visibility = View.INVISIBLE
            loginButton.text = "Login"
            parentDbName = "Users"
        }
    }

    private fun loginUser() {
        val phone = loginPhoneInput.getText().toString()
        val password = loginPasswordInput.getText().toString()
        if (TextUtils.isEmpty(phone)) {
            loadingBar.dismiss()
            Toast.makeText(this, "Введите номер телефона", Toast.LENGTH_SHORT).show()
        }
        if (TextUtils.isEmpty(password)) {
            loadingBar.dismiss()
            Toast.makeText(this, "Введите пароль", Toast.LENGTH_SHORT).show()
        } else {
            loadingBar.setTitle("Вход в приложение")
            loadingBar.setMessage("Пожалуйста, подождите")
            loadingBar.setCanceledOnTouchOutside(false)
            loadingBar.show()
            ValidateUser(phone, password)
        }
    }

    private fun ValidateUser(phone: String, password: String) {
        if (rememberMe.isChecked) {
            Paper.book().write(Prevalent.UserPhoneKey, phone)
            Paper.book().write(Prevalent.UserPasswordKey, password)
        }
        val RootRef: DatabaseReference
        RootRef = FirebaseDatabase.getInstance().getReference()
        RootRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child(parentDbName).child(phone).exists()) {
                    val usersData: Users? = snapshot.child(parentDbName).child(phone).getValue(
                        Users::class.java
                    )
                    if (usersData?.getPhone().equals(phone)) {
                        if (usersData?.getPassword().equals(password)) {
                            if (parentDbName == "Users") {
                                loadingBar.dismiss()
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Успешный вход",
                                    Toast.LENGTH_LONG
                                ).show()
                                val homeIntent = Intent(
                                    this@LoginActivity,
                                    HomeFragment::class.java
                                )
                                startActivity(homeIntent)
                            } else if (parentDbName == "Admins") {
                                loadingBar.dismiss()
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Успешный вход",
                                    Toast.LENGTH_LONG
                                ).show()
//                                val homeIntent = Intent(
//                                    this@LoginActivity,
//                                    AdminCategoryActivity::class.java
//                                )
//                                startActivity(homeIntent)
                            }
                        } else {
                            loadingBar.dismiss()
                            Toast.makeText(
                                this@LoginActivity,
                                "Пароль не подходит",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                } else {
                    loadingBar.dismiss()
                    Toast.makeText(
                        this@LoginActivity,
                        "Номер телефона не существует",
                        Toast.LENGTH_LONG
                    ).show()
                    val registerIntent = Intent(
                        this@LoginActivity,
                        RegisterActivity::class.java
                    )
                    startActivity(registerIntent)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}