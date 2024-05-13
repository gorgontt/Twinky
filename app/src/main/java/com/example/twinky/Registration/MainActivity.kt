package com.example.twinky.Registration

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.twinky.HomeActivity
import com.example.twinky.Model.Users
import com.example.twinky.Prevalent.Prevalent
import com.example.twinky.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.paperdb.Paper

class MainActivity : AppCompatActivity() {

    private lateinit var registerBtn: Button
    private lateinit var loginBtn: Button
    private lateinit var loadingBar: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        registerBtn = findViewById(R.id.main_registration_btn)
        loginBtn = findViewById(R.id.main_login_btn)
        loadingBar = ProgressDialog(this)
        Paper.init(this)

        loginBtn.setOnClickListener(View.OnClickListener { v: View? ->
            val loginIntent = Intent(
                this@MainActivity,
                LoginActivity::class.java
            )
            startActivity(loginIntent)
        })
        registerBtn.setOnClickListener(View.OnClickListener { v: View? ->
            val registrtIntent = Intent(
                this@MainActivity,
                RegisterActivity::class.java
            )
            startActivity(registrtIntent)
        })
        val UserPhoneKey = Paper.book().read<String>(Prevalent.UserPhoneKey)
        val UserPasswordKey = Paper.book().read<String>(Prevalent.UserPasswordKey)
        if (UserPhoneKey !== "" && UserPasswordKey !== "") {
            if (!TextUtils.isEmpty(UserPhoneKey) && !TextUtils.isEmpty(UserPasswordKey)) {
                ValidateUser(UserPhoneKey, UserPasswordKey)
                loadingBar!!.setTitle("Log in to the app")
                loadingBar!!.setMessage("Please wait")
                loadingBar!!.setCanceledOnTouchOutside(false)
                loadingBar!!.show()
            }
        }
    }

    private fun ValidateUser(phone: String?, password: String?) {
        val RootRef: DatabaseReference
        RootRef = FirebaseDatabase.getInstance().getReference()
        RootRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child("Users").child(phone!!).exists()) {
                    val usersData: Users? =
                        snapshot.child("Users").child(phone).getValue(Users::class.java)
                    if (usersData?.getPhone().equals(phone)) {
                        if (usersData?.getPassword().equals(password)) {
                            loadingBar.dismiss()
                            Toast.makeText(this@MainActivity, "Successfully", Toast.LENGTH_LONG)
                                .show()
                            val homeIntent = Intent(
                                this@MainActivity,
                                HomeActivity::class.java
                            )
                            startActivity(homeIntent)
                        } else {
                            loadingBar.dismiss()
                        }
                    }
                } else {
                    loadingBar!!.dismiss()
                    Toast.makeText(
                        this@MainActivity,
                        "Phone number doesn't exists",
                        Toast.LENGTH_LONG
                    ).show()
                    val registerIntent = Intent(
                        this@MainActivity,
                        RegisterActivity::class.java
                    )
                    startActivity(registerIntent)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

}