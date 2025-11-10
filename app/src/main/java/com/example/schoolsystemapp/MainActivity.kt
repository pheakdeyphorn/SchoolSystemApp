package com.example.schoolsystemapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * The main activity that serves as a single container for all Fragments in the application.
 * It is decorated with @AndroidEntryPoint for Hilt to inject dependencies.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        // 1. Apply the application theme defined in res/values/themes.xml
        // This must be done before super.onCreate()
        setTheme(R.style.Theme_SchoolSystemApp)
        super.onCreate(savedInstanceState)

        // 2. Set the content view to the XML layout
        // activity_main.xml contains the NavHostFragment that hosts your UI.
        setContentView(R.layout.activity_main)
    }
}