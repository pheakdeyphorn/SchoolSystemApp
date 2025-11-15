package com.example.schoolsystemapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schoolsystemapp.data.model.User
import com.example.schoolsystemapp.data.repository.AuthRepository
import com.example.schoolsystemapp.util.Resource // Ensure your Resource sealed interface is imported
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for handling all authentication-related business logic,
 * communicating with the AuthRepository, and exposing results to the UI.
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    // --- Sign Up State Management ---
    private val _signUpState = MutableLiveData<Resource<User>?>()
    val signUpState: LiveData<Resource<User>?> = _signUpState

    fun signUpUser(email: String, password: String, isTeacher: Boolean) {
        // FIX: Check against the singleton object directly
        if (_signUpState.value is Resource.Loading) return

        // FIX: Reference the singleton object directly, without parentheses
        _signUpState.value = Resource.Loading

        viewModelScope.launch {
            val result = repository.signUpWithEmail(email, password, isTeacher)
            _signUpState.postValue(result)
        }
    }

    fun clearSignUpState() {
        _signUpState.value = null
    }

    // ----------------------------------------------------------------------------------
    // Sign In Logic
    // ----------------------------------------------------------------------------------

    private val _signInState = MutableLiveData<Resource<User>?>()
    val signInState: LiveData<Resource<User>?> = _signInState

    /**
     * Initiates the user sign-in process with email and password.
     */
    fun signInUser(email: String, password: String) {
        // FIX: Check against the singleton object directly
        if (_signInState.value is Resource.Loading) return

        // FIX: Reference the singleton object directly, without parentheses
        _signInState.value = Resource.Loading

        viewModelScope.launch {
            val result = repository.signInWithEmail(email, password)
            _signInState.postValue(result)
        }
    }

    /**
     * Clears the sign-in state after a navigation or state consumption.
     */
    fun clearSignInState() {
        _signInState.value = null
    }

    // ----------------------------------------------------------------------------------
    // Dashboard Functionality
    // ----------------------------------------------------------------------------------

    /**
     * Retrieves the currently authenticated user's details, including their role.
     */
    fun getCurrentUserRole(): User? {
        return repository.getCurrentUser()
    }

    /**
     * Signs the user out of Firebase and clears the Auth state.
     */
    fun signOut() {
        viewModelScope.launch {
            repository.signOut()
        }
    }

    // Helper function to check if a user is already authenticated
    fun isAuthenticated(): Boolean {
        return repository.isAuthenticated()
    }
}