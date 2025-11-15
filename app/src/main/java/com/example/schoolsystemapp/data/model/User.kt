package com.example.schoolsystemapp.data.model

data class User(
    val uid: String,
    val email: String?,
    // CRITICAL: Add the role flag for navigation logic
    val isTeacher: Boolean = false
)