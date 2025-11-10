package com.example.schoolsystemapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * The custom Application class required by Hilt.
 * The @HiltAndroidApp annotation triggers Hilt's code generation
 * for the entire application's dependency graph.
 */
@HiltAndroidApp
class SchoolSystemApplication : Application() {
    // You can leave the body empty for now.
}
