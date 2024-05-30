package com.example.twinky.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twinky.Models.Post
import com.example.twinky.Models.Reel
import com.example.twinky.SignUpActivity
import com.example.twinky.adapter.PostAdapter
import com.example.twinky.adapter.ReelAdapter
import com.example.twinky.databinding.FragmentChatBinding
import com.example.twinky.databinding.FragmentHomeBinding
import com.example.twinky.post.NewMessageActivity
import com.example.twinky.utils.POST
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


class ChatFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding
    private var postList = ArrayList<Post>()
    private lateinit var adapter: ReelAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChatBinding.inflate(inflater, container, false)
        adapter = ReelAdapter(requireContext(), postList)
        binding.chatRv.layoutManager = LinearLayoutManager(requireContext())
        binding.chatRv.adapter = adapter


        Firebase.firestore.collection(POST).get().addOnSuccessListener {

            var tempList = ArrayList<Post>()
            postList.clear()

            for (i in it.documents){

                var post: Post = i.toObject<Post>()!!
                tempList.add(post)
            }
            postList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }


        return binding.root
    }
}