package com.example.twinky.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.twinky.Models.Reel
import com.example.twinky.SignUpActivity
import com.example.twinky.adapter.ReelAdapter
import com.example.twinky.databinding.FragmentReelBinding
import com.example.twinky.post.NewMessageActivity
import com.example.twinky.utils.REEL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class ReelFragment : Fragment() {

    private lateinit var binding: FragmentReelBinding
    lateinit var adapter: ReelAdapter
    var reelList = ArrayList<Reel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReelBinding .inflate(inflater, container, false)
     /*   adapter = ReelAdapter(requireContext(), reelList)
        binding.viewPager.adapter = adapter
        Firebase.firestore.collection(REEL).get().addOnSuccessListener {
            var tempList = ArrayList<Reel>()
            reelList.clear()

            for (i in it.documents) {

                var reel = i.toObject<Reel>()!!
                tempList.add(reel)
            }
            reelList.addAll(tempList)
            reelList.reverse()
            adapter.notifyDataSetChanged()
        }

      */

        binding.add.setOnClickListener {
            val intent = Intent(requireContext(), NewMessageActivity::class.java )
            startActivity(intent)
        }

        binding.logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intentLog = Intent(requireContext(), SignUpActivity::class.java)
            intentLog.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intentLog)
        }

        return binding.root
    }

    companion object {

    }
}