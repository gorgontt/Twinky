package com.example.twinky.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.twinky.Models.Reel
import com.example.twinky.SignUpActivity
import com.example.twinky.adapter.MyReelAdapter
import com.example.twinky.databinding.FragmentMyReelsBinding
import com.example.twinky.post.NewMessageActivity
import com.example.twinky.utils.REEL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


class MyReelsFragment : Fragment() {

    private lateinit var binding: FragmentMyReelsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyReelsBinding.inflate(inflater, container, false)

    /*    binding.add.setOnClickListener {
            val intent = Intent(requireContext(), NewMessageActivity::class.java )
            startActivity(intent)
        }

        binding.logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intentLog = Intent(requireContext(), SignUpActivity::class.java)
            intentLog.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intentLog)
        }

     */

        return binding.root
    }



    companion object {

    }
}