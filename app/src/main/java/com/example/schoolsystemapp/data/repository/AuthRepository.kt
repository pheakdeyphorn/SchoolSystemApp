package com.example.schoolsystemapp.data.repository

import com.example.schoolsystemapp.data.model.User
import com.example.schoolsystemapp.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * Repository class responsible for all Firebase Authentication and initial Firestore operations.
 */
class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) {
    // Private variable to cache the last successfully signed-in user object with role data.
    private var _currentUser: User? = null

    // --- AUTHENTICATION (Firebase Auth & Firestore) ---

    /**
     * Creates a new user with email and password and creates an initial profile in Firestore.
     */
    suspend fun signUpWithEmail(email: String, password: String, isTeacher: Boolean): Resource<User> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user

            if (firebaseUser != null) {
                // 1. Create the User object with the role
                val newUser = User(
                    uid = firebaseUser.uid,
                    email = firebaseUser.email,
                    isTeacher = isTeacher
                )

                // 2. Save the user's role to Firestore (mandatory for dashboard logic)
                db.collection("users").document(firebaseUser.uid)
                    .set(newUser) // Saves the entire User object
                    .await()

                // Cache the user locally after successful sign-up
                _currentUser = newUser
                Resource.Success(newUser)
            } else {
                Resource.Error("Sign-up failed.")
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An unknown error occurred during sign-up.")
        }
    }

    /**
     * Signs in an existing user with email and password.
     * CRITICAL FIX: Must fetch the role data from Firestore after successful authentication.
     */
    suspend fun signInWithEmail(email: String, password: String): Resource<User> {
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user

            if (firebaseUser != null) {
                // 1. Authenticated successfully. Now fetch the role from Firestore.
                val docSnapshot = db.collection("users").document(firebaseUser.uid)
                    .get()
                    .await()

                // 2. Map the Firestore document to our User model
                val fullUser = docSnapshot.toObject(User::class.java)

                if (fullUser != null) {
                    // Cache and return the full User object with the role
                    _currentUser = fullUser
                    Resource.Success(fullUser)
                } else {
                    // This scenario means Auth worked, but Firestore data is missing (corrupted state)
                    Resource.Error("User data is missing. Please contact support.")
                }
            } else {
                Resource.Error("Sign-in failed. Please check your credentials.")
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An unknown error occurred during sign-in.")
        }
    }

    // --- NEW DASHBOARD FUNCTIONS ---

    /**
     * Checks if a user is currently logged in and returns the cached User object (with role).
     * This is non-suspending for fast access in the ViewModel.
     *
     * @return The cached User model (with role) if logged in, null otherwise.
     */
    fun getCurrentUser(): User? {
        // First, ensure Firebase still thinks the user is logged in
        if (auth.currentUser == null) {
            _currentUser = null
            return null
        }
        // Then, return the cached User object (with the role flag)
        return _currentUser
    }

    /**
     * Signs out the current user and clears the local cache.
     */
    suspend fun signOut() {
        auth.signOut()
        _currentUser = null // Clear the local cache
    }

    /**
     * Checks if a Firebase user is currently logged in.
     */
    fun isAuthenticated(): Boolean {
        return auth.currentUser != null
    }
}