package com.example.myapplication.fragments.login

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R

class ChoiceFragment : Fragment(R.layout.fragment_choice) {
    lateinit var logbtn : Button
    lateinit var regbtn : Button
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        logbtn = view.findViewById(R.id.loginbtn1)
        regbtn = view.findViewById(R.id.registerbtn1)

        regbtn.setOnClickListener {
            navController.navigate(R.id.action_accountOptionsFragment_to_registerFragment)
        }

        logbtn.setOnClickListener{
            navController.navigate(R.id.action_accountOptionsFragment_to_loginFragment)
        }

    }

}
