package com.example.myapplication.presentation.checkout

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAfterCheckOutBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class AfterCheckOutFragment : Fragment() {
    private var _binding: FragmentAfterCheckOutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAfterCheckOutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Hide the ActionBar
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        // Hide the BottomNavigationView
        val bottomNavBar = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavBar.visibility = View.GONE

        val navController = findNavController()
        val sharedPreferences = requireContext().getSharedPreferences("userData", Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString("username", "")
        val address = sharedPreferences.getString("address", "")
        binding.usernameTv.text = userName
        binding.addressTvAfterPurchase.text = address

        binding.continueShoppingBtn.setOnClickListener {
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.home_fragment, true) // Clear the back stack up to HomeFragment
                .build()
            findNavController().navigate(R.id.home_fragment, null, navOptions)
        }

        // Handle the back button press
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.home_fragment, true) // Clear the back stack up to HomeFragment
                .build()
            findNavController().navigate(R.id.home_fragment, null, navOptions)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
