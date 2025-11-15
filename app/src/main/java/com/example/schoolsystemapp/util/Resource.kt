package com.example.schoolsystemapp.util

/**
 * A sealed class used to wrap network responses or resource operations with status information.
 * This helps manage the state of asynchronous operations (loading, success, or error) in the ViewModel.
 */
sealed interface Resource<out T> {
    // Represents a successful operation with the result data (T).
    data class Success<T>(val data: T) : Resource<T>

    // Represents a failed operation with an optional error message.
    data class Error(val message: String) : Resource<Nothing>

    // Represents an ongoing operation (e.g., waiting for Firebase response).
    data object Loading : Resource<Nothing>
}