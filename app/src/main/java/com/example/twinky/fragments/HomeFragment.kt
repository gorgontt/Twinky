package com.example.twinky.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.twinky.R
import com.example.twinky.SignUpActivity
import com.example.twinky.databinding.FragmentHomeBinding
import com.example.twinky.databinding.FragmentProfileBinding


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.exitBtn.setOnClickListener {
            val intent = Intent(activity, SignUpActivity::class.java)
            activity?.startActivity(intent)
            activity?.finish()
        }
        return binding.root


    }

    companion object {

    }
}