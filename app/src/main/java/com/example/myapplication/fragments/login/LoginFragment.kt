package com.example.myapplication.fragments.login

import android.content.Context
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.data.local.UserDatabase
import com.example.myapplication.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch

class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()

        binding.signUpTv.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.loginBtn.setOnClickListener {

            val emailInput = binding.emailEt.text.toString()
            val passInput = binding.PassEt.text.toString()

            if (emailInput.isEmpty() || passInput.isEmpty()) {
                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
                Toast.makeText(context, "invalid email or password", Toast.LENGTH_SHORT).show()
            }
            else if (passInput.length < 8){
                Toast.makeText(context, "invalid email or password", Toast.LENGTH_SHORT).show()
            }
            else {
                val userDao = UserDatabase.getDatabase(requireContext()).userDao()

                lifecycleScope.launch {
                    val user = userDao.login(emailInput,passInput)
                    if (user != null){

                        Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                        val fullname =  user.fullname
                        val username = user.username
                        val email = user.email
                        val phonenumber = user.phoneNumber
                        val address = user.address
                        val gender=user.gender

                        val sharedPreferences = requireContext().getSharedPreferences("userData" , Context.MODE_PRIVATE)
                        sharedPreferences.edit().apply{
                            putString("fullname",fullname)
                            putString("username" , username)
                            putString("email",email)
                            putString("phonenumber",phonenumber)
                            putString("address",address)
                            putString("gender",gender)
                            putBoolean("isLoggedIn", true)
                            apply()
                        }

                        navController.navigate(R.id.action_loginFragment_to_home_fragment)
                    }
                    else {
                        Toast.makeText(context, "no such a user", Toast.LENGTH_SHORT).show()
                    }
                }
            }


        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}