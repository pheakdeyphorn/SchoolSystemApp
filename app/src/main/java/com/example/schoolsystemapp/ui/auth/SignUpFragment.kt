package com.example.schoolsystemapp.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.schoolsystemapp.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragment responsible for handling user sign-up registration.
 * It provides navigation back to the Login screen.
 */
@AndroidEntryPoint
class SignUpFragment : Fragment() {

    // View Binding setup
    private var _binding: FragmentSignUpBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment using View Binding
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Handle the Back Button (btn_back_signup) click
        binding.btnBackSignup.setOnClickListener {
            // navigateUp() is the standard way to return to the previous destination (LoginFragment)
            findNavController().navigateUp()
        }

        // TODO: We will add the actual registration logic for the Sign Up button (btn_signup)
        // later, once we integrate the Auth ViewModel and Firebase.
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clear the binding object reference to avoid memory leaks
        _binding = null
    }
}