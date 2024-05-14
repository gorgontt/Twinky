package com.example.twinky.ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.twinky.R
import com.example.twinky.Registration.LoginActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.rey.material.app.BottomSheetDialog
import com.rey.material.widget.TextView


class ProfileFragment : Fragment() {

    private lateinit var dialog: BottomSheetDialog

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val settings = view.findViewById<ImageView>(R.id.profile_settings)

        settings.setOnClickListener {
            showBottomSheet()
        }

        val userPhone = "USER_PHONE_NUMBER"  // здесь должен быть телефон зарегистрированного пользователя
        val rootRef = FirebaseDatabase.getInstance().getReference()

        rootRef.child("Users").child(userPhone).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val userName = snapshot.child("name").getValue(String::class.java)
                    view.findViewById<TextView>(R.id.profile_nickname).text = userName
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("ProfileFragment", "Failed to read user data", error.toException())
            }
        })
    }

    private fun showBottomSheet() {
        val dialogView = layoutInflater.inflate(R.layout.bottom_sheet_settings, null)
        dialog = BottomSheetDialog(activity, R.style.BottomSheetDialogTheme)
        dialog.setContentView(dialogView)
        dialog.show()
    }



}