package com.example.twinky.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twinky.Models.Post
import com.example.twinky.adapter.ReelAdapter
import com.example.twinky.databinding.FragmentReelBinding
import com.example.twinky.utils.POST
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


class ReelFragment : Fragment() {

    private lateinit var binding: FragmentReelBinding
    private var postList = ArrayList<Post>()
    private lateinit var adapter: ReelAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReelBinding.inflate(inflater, container, false)
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