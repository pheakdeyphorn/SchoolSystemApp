package com.example.schoolsystemapp.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.schoolsystemapp.R
import com.example.schoolsystemapp.databinding.FragmentWelcomeBinding // Replace with your actual binding class name

class WelcomeFragment : Fragment() {

    private var _binding: FragmentWelcomeBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 1. Inflate the layout using Data Binding
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 2. Set click listener for the Teacher Login button
        binding.btnTeacherLogin.setOnClickListener {
            // Navigate to LoginFragment using the defined action ID
            findNavController().navigate(R.id.action_welcomeFragment_to_loginFragment)
        }

        // 3. Set click listener for the Student Login button (also goes to Login for now)
        binding.btnStudentLogin.setOnClickListener {
            // Navigate to LoginFragment using the defined action ID
            findNavController().navigate(R.id.action_welcomeFragment_to_loginFragment)
        }

        // 4. Set click listener for the Sign Up text link
        binding.btnSignupText.setOnClickListener {
            // Navigate to SignUpFragment using the defined action ID
            findNavController().navigate(R.id.action_welcomeFragment_to_signUpFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}