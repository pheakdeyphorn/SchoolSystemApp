package com.example.schoolsystemapp.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.schoolsystemapp.R
import com.example.schoolsystemapp.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragment responsible for handling user login.
 * It provides navigation back to Welcome and forward to Sign Up.
 */
@AndroidEntryPoint
class LoginFragment : Fragment() {

    // View Binding setup
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment using View Binding
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Handle the Back Button (btn_back) click
        binding.btnBack.setOnClickListener {
            // navigateUp() is the standard way to return to the previous fragment
            findNavController().navigateUp()
        }

        // 2. Handle the Sign Up Text Link (btn_signup_text) click
        binding.btnSignupText.setOnClickListener {
            // Navigate to SignUpFragment using the action defined in auth_nav_graph.xml
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        // TODO: The actual login logic (for btn_login) will be added here later
        // once we integrate the Auth ViewModel and Firebase.
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clear the binding object reference to avoid memory leaks
        _binding = null
    }
}