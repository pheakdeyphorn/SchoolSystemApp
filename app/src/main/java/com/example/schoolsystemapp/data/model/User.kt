package com.example.schoolsystemapp.data.model

data class User(
    // Unique identifier for the user
    val id: String,
    // User's full name for display in the UI
    val name: String,
    // User's email address
    val email: String,
    // The role, using the safe enum defined below
    val role: UserRole,
    // The security token received on login, required for subsequent API calls
    val token: String? = null
)