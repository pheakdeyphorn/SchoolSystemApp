package com.example.schoolsystemapp.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.schoolsystemapp.databinding.FragmentSignUpBinding
import com.example.schoolsystemapp.util.Resource
import com.example.schoolsystemapp.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragment for user sign-up with email and password, and selection of role (Student/Teacher).
 */
@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    // Inject the AuthViewModel using the viewModels delegate
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 1. Inflate the layout using Data Binding
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        observeSignUpState()
    }

    /**
     * Sets up click listeners for the back button and the sign-up button.
     */
    private fun setupListeners() {
        // Navigate back to the previous screen (WelcomeFragment or LoginFragment)
        binding.btnBackSignup.setOnClickListener {
            findNavController().popBackStack()
        }

        // Handle Sign Up button click
        binding.btnSignup.setOnClickListener {
            handleSignUp()
        }
    }

    /**
     * Handles the collection of user input and initiates the sign-up process.
     */
    private fun handleSignUp() {
        val email = binding.etEmailSignup.text.toString().trim()
        val password = binding.etPasswordSignup.text.toString()
        val confirmPassword = binding.etConfirmPassword.text.toString()

        // Determine if the user selected 'Teacher' role
        val isTeacher = binding.radioGroupRole.checkedRadioButtonId == binding.rbTeacher.id

        // Basic validation checks
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(context, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(context, "Passwords do not match.", Toast.LENGTH_SHORT).show()
            return
        }

        if (password.length < 6) {
            Toast.makeText(context, "Password must be at least 6 characters long.", Toast.LENGTH_SHORT).show()
            return
        }

        // Call the ViewModel to start the sign-up process
        viewModel.signUpUser(email, password, isTeacher)
    }

    /**
     * Observes the LiveData from the ViewModel to update UI based on sign-up status.
     */
    private fun observeSignUpState() {
        viewModel.signUpState.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    // Show loading indicator and disable the button
                    binding.progressBarSignup.isVisible = true
                    binding.btnSignup.isEnabled = false
                }
                is Resource.Success -> {
                    // Hide loading, enable button
                    binding.progressBarSignup.isVisible = false
                    binding.btnSignup.isEnabled = true

                    Toast.makeText(context, "Sign Up Successful! Redirecting...", Toast.LENGTH_LONG).show()
                    Log.d("SignUpFragment", "User signed up: ${resource.data.uid}, Role: ${if (resource.data.email != null) "Assigned" else "Unknown"}")

                    // CRITICAL: Navigate to the appropriate next screen (Dashboard)
                    // We will set up the actual dashboard navigation later. For now, let's navigate back to Welcome.
                    // This is a placeholder for actual dashboard navigation based on user role.
                    findNavController().navigate(com.example.schoolsystemapp.R.id.welcomeFragment)

                    // Clear the state to prevent re-triggering navigation on configuration change (like rotation)
                    viewModel.clearSignUpState()
                }
                is Resource.Error -> {
                    // Hide loading, enable button
                    binding.progressBarSignup.isVisible = false
                    binding.btnSignup.isEnabled = true

                    // Show error message to the user
                    Toast.makeText(context, "Sign Up Failed: ${resource.message}", Toast.LENGTH_LONG).show()
                    Log.e("SignUpFragment", "Sign Up Error: ${resource.message}")

                    // Clear the state
                    viewModel.clearSignUpState()
                }
                null -> {
                    // Initial state or cleared state. Ensure UI is reset.
                    binding.progressBarSignup.isVisible = false
                    binding.btnSignup.isEnabled = true
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}