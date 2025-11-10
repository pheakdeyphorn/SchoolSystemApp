package com.example.schoolsystemapp.data.model

enum class UserRole {
    STUDENT,
    TEACHER,
    ADMIN,
    // A fallback role if the server sends something unexpected
    UNKNOWN
}