package com.example.myapplication.fragments.resigstraion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.data.local.User
import com.example.myapplication.data.local.UserDatabase
import com.example.myapplication.databinding.FragmentRegisterBinding
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {
    //private lateinit var viewModel: RegisterFragmentViewModel
    private lateinit var emailView: TextInputLayout
    private lateinit var usernameView: TextInputLayout
    private lateinit var passwordView: TextInputLayout
    private lateinit var confirmPasswordView: TextInputLayout
    private lateinit var registerBtn: Button
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()

        binding.signInTvSignUpPage.setOnClickListener{
            navController.navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.signUpBtnSignUpPage.setOnClickListener {
            val fullnameInput= binding.fullnameEtSignUpPage.text.toString()
            val usernameInput = binding.usernameEtSignUpPage.text.toString()
            val emailInput = binding.emailEtSignUpPage.text.toString()
            val phoneInput = binding.phoneEtSignUpPage.text.toString()
            val addressInput = binding.addressEtSignUpPage.text.toString()
            val genderInput = binding.genderSpinner.selectedItem.toString()
            val passwordInput = binding.PassEtSignUpPage.text.toString()
            val confirmPasswordInput = binding.cPassEtSignUpPage.text.toString()

            if (usernameInput.isEmpty() || emailInput.isEmpty() || phoneInput.isEmpty() || addressInput.isEmpty() || genderInput.isEmpty() ||passwordInput.isEmpty() || confirmPasswordInput.isEmpty()) {
                Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show()
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {

                Toast.makeText(context, "Please enter a valid email", Toast.LENGTH_SHORT).show()

            }

            /*else if (Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
                emailInput.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,ContextCompat.getDrawable(requireContext(),
                    R.drawable.ic_check),
                    null)
            }*/

            else if(fullnameInput.length < 6){

                Toast.makeText(context, "Full Name must be at least 6 characters", Toast.LENGTH_SHORT).show()

            }

            else if (!android.util.Patterns.PHONE.matcher(phoneInput).matches()) {
                Toast.makeText(context, "Please enter a valid phone number", Toast.LENGTH_SHORT).show()

            }
            else if (passwordInput.length < 8 ){
                Toast.makeText(context, "Password must be at least 8 characters", Toast.LENGTH_SHORT).show()
            }
            else if (passwordInput != confirmPasswordInput) {
                Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else {
                val userDao = UserDatabase.getDatabase(requireContext()).userDao()
                lifecycleScope.launch {
                    val doesemailexist = userDao.doesEmailExist(emailInput)
                    val doesuserexist = userDao.doesUserNameExist(usernameInput)

                    if (doesemailexist) {
                        Toast.makeText(context, "Email is already in use", Toast.LENGTH_SHORT)
                            .show()
                    } else if (doesuserexist) {
                        Toast.makeText(context, "User Name is already in use", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        userDao.insertUser(
                            User(
                                fullname = fullnameInput,
                                username = usernameInput,
                                email = emailInput,
                                phoneNumber = phoneInput,
                                address = addressInput,
                                gender = genderInput,
                                password = passwordInput
                            )
                        )
                        Toast.makeText(context, "Signup Successful!", Toast.LENGTH_SHORT).show()
                        navController.navigate(R.id.action_registerFragment_to_loginFragment)
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