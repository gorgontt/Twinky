package com.example.twinky.ui.profile

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.twinky.R
import com.example.twinky.databinding.FragmentHomeBinding
import com.example.twinky.databinding.FragmentProfileBinding
import com.example.twinky.ui.home.HomeViewModel
import com.google.android.material.button.MaterialButton
import com.rey.material.app.BottomSheetDialog

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val btnToDialogWindow:MaterialButton= binding.btnToRegisterActivity

        btnToDialogWindow.setOnClickListener {

            val view:View = layoutInflater.inflate(R.layout.layout_bottom_sheet, null)
            val dialog = BottomSheetDialog(requireContext())
            val btnToRegister = view.findViewById<MaterialButton>(R.id.bottom_btn_register)
            val btnToLogin = view.findViewById<MaterialButton>(R.id.bottom_btn_login)

            btnToRegister.setOnClickListener {
                val intentToRegister = Intent(activity, RegisterActivity::class.java)
                startActivity(intentToRegister)
            }

            dialog.setContentView(view)
            dialog.show()
       /*     val bottomSheetDialog = BottomSheetDialog(requireContext())
            val bottomSheetView = LayoutInflater.from(requireContext()).inflate(
                R.layout.layout_bottom_sheet,
                requireActivity().findViewById<LinearLayout>(R.id.bottom_sheet_container)!!
            )
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()

        */

        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}