package com.example.myapplication.presentation.profile_feature

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentProfileFragmentBinding

class FragmentProfile : Fragment() {
    private var _binding: FragmentProfileFragmentBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navController = findNavController()

        // Access the SharedPreferences
        super.onViewCreated(view, savedInstanceState)

        // Retrieve data
        val sharedPreferences = requireContext().getSharedPreferences("userData", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username","")
        val email = sharedPreferences.getString("email", "")
        val fullname = sharedPreferences.getString("fullname","")
        val phoneNumber = sharedPreferences.getString("phonenumber", "")
        val address = sharedPreferences.getString("address", "")
        val gender=sharedPreferences.getString("gender","")

        // Set the retrieved values to TextViews using view binding
        binding.userNameTv.text = username
        binding.emailAddressTv.text = email
        binding.name.text = fullname
        binding.number.text = phoneNumber
        binding.addressContent.text = address
        binding.genderType.text=gender

        //logout button action

        binding.logoutBtn.setOnClickListener {
            showLogoutConfirmationDialog(navController)
        }


    }

    private fun showLogoutConfirmationDialog(navController: androidx.navigation.NavController) {
        // Create the AlertDialog
        AlertDialog.Builder(requireContext())
            .setTitle("Logout")
            .setMessage("Are you sure you want to log out?")
            .setPositiveButton("Yes") { dialog, _ ->
                // If "Yes" is clicked, navigate to the account options fragment
                navController.navigate(R.id.accountOptionsFragment)
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                // If "No" is clicked, just dismiss the dialog
                dialog.dismiss()
            }
            .create()
            .show()
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentProfile()
    }
}
