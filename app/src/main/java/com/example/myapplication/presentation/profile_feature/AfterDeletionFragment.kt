package com.example.myapplication.presentation.profile_feature

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.data.local.UserDatabase
import com.example.myapplication.databinding.FragmentAfterDeletionBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class AfterDeletionFragment : Fragment() {

    private var _binding: FragmentAfterDeletionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAfterDeletionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // In your Fragment or Activity
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        // In your Fragment or Activity
        val bottomNavBar = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavBar.visibility = View.GONE


        val sharedPreferences = requireContext().getSharedPreferences("userData", Context.MODE_PRIVATE)
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        val userName=sharedPreferences.getString("username","")
        binding.userNameTvAfterDeletionFragment.text=userName

        binding.proccedToDeletionBtn.setOnClickListener {

            showDeletionConfirmationDialog(navController, userName.toString())

        }



        binding.cancelDeletionProcessBtn.setOnClickListener {
            navController.navigate(R.id.home_fragment)

        }

    }



    private fun showDeletionConfirmationDialog(navController: androidx.navigation.NavController, userName: String) {
        val userDao = UserDatabase.getDatabase(requireContext()).userDao()

        // Create the AlertDialog
        AlertDialog.Builder(requireContext())
            .setTitle("Account Delete")
            .setMessage("Are you sure you want to delete your account? This action is permanent and can't be undone.")
            .setPositiveButton("Yes") { dialog, _ ->
                // Launch a coroutine to handle the suspend function
                lifecycleScope.launch {
                    // Delete the user
                    userDao.deleteUserByUserName(userName)
                    requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                        val navOptions = NavOptions.Builder()
                            .setPopUpTo(navController.graph.startDestinationId, true) // Clear the back stack up to HomeFragment
                            .build()

                        findNavController().navigate(navController.graph.startDestinationId ,null, navOptions)
                    }
                    findNavController().navigate(R.id.loginFragment)





                    Toast.makeText(requireContext(), "Account deleted successfully", Toast.LENGTH_SHORT).show()

                    // Navigate to the account options fragment

                }

                // Dismiss the dialog
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                // If "No" is clicked, just dismiss the dialog
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
