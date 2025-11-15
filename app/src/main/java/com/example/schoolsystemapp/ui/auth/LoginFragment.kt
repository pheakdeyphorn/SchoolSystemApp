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
import com.example.schoolsystemapp.R
import com.example.schoolsystemapp.databinding.FragmentLoginBinding
import com.example.schoolsystemapp.util.Resource
import com.example.schoolsystemapp.viewmodel.AuthViewModel
import com.example.schoolsystemapp.data.model.User // IMPORTANT: Ensure this import is present
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragment for user login with email and password.
 */
@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    // Inject the AuthViewModel using the viewModels delegate
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 1. Inflate the layout using Data Binding
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        // 2. Set the viewModel variable in the layout for two-way binding (if implemented)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        observeSignInState()
    }

    private fun setupListeners() {
        // Navigate back (using the NavController popBackStack)
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        // Navigate to Sign Up screen using the ACTION ID defined in auth_nav_graph.xml
        binding.tvSignUp.setOnClickListener {
            // FIX: Use the action ID for reliable navigation
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        // Login Button Click Handler
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmailLogin.text.toString().trim()
            val password = binding.etPasswordLogin.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Please enter both email and password.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Trigger ViewModel sign-in
            viewModel.signInUser(email, password)
        }
    }

    private fun observeSignInState() {
        // Observe the signInState LiveData for updates
        viewModel.signInState.observe(viewLifecycleOwner) { resource ->
            // Handle null state explicitly for safety
            if (resource == null) {
                binding.progressBarLogin.isVisible = false
                binding.btnLogin.isEnabled = true
                return@observe
            }

            when (resource) {
                is Resource.Loading -> {
                    binding.progressBarLogin.isVisible = true
                    binding.btnLogin.isEnabled = false
                }
                is Resource.Success -> {
                    binding.progressBarLogin.isVisible = false
                    binding.btnLogin.isEnabled = true

                    val user = resource.data

                    if (user != null) {
                        Toast.makeText(context, "Login Successful! Redirecting...", Toast.LENGTH_LONG).show()
                        Log.d("LoginFragment", "User signed in: ${user.uid}, Role: ${if(user.isTeacher) "Teacher" else "Student"}")

                        // FIX: Navigate using the ACTION ID (action_loginFragment_to_dashboardFragment)
                        // This action was configured in auth_nav_graph.xml to clear the back stack.
                        findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
                    } else {
                        Toast.makeText(context, "Login Successful, but user data is missing.", Toast.LENGTH_LONG).show()
                    }

                    // Clear the state using the ViewModel function
                    viewModel.clearSignInState()
                }
                is Resource.Error -> {
                    findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)

                    viewModel.clearSignInState()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}