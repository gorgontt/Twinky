package com.example.twinky.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twinky.Models.User
import com.example.twinky.adapter.SearchAdapter
import com.example.twinky.databinding.FragmentSearchBinding
import com.example.twinky.utils.USER_NODE
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    lateinit var adapter: SearchAdapter
    var userList = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding .inflate(inflater, container, false)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = SearchAdapter(requireContext(), userList)
        binding.recyclerView.adapter = adapter

        Firebase.firestore.collection(USER_NODE).get().addOnSuccessListener {

            var tempList = ArrayList<User>()
            userList.clear()
            for (i in it.documents){
                if (i.id.toString().equals(Firebase.auth.currentUser!!.uid.toString())){

                }else{

                    var user: User = i.toObject<User>()!!
                    tempList.add(user)
                }

            }
            userList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }


        binding.searchBtn.setOnClickListener {

            var text = binding.searchView.text.toString()

            Firebase.firestore.collection(USER_NODE).whereEqualTo("userName", text).get().addOnSuccessListener {

                var tempList = ArrayList<User>()
                userList.clear()

                if (it.isEmpty){

                }else{

                    for (i in it.documents){
                        if (i.id.toString().equals(Firebase.auth.currentUser!!.uid.toString())){

                        }else{

                            var user: User = i.toObject<User>()!!
                            tempList.add(user)
                        }

                    }
                    userList.addAll(tempList)
                    adapter.notifyDataSetChanged()
                }

            }

        }


        return binding.root
    }

    companion object {

    }
}