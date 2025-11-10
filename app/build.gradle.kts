plugins {
    // Standard Android Application plugin
    id("com.android.application")

    // Kotlin Android plugin
    kotlin("android")

    // Hilt Kapt/KSP and Plugin for dependency injection
    kotlin("kapt")
    alias(libs.plugins.hilt)
    // KTX plugins (e.g., ViewModels, LiveData)
}

android {
    namespace = "com.example.schoolsystemapp"
    compileSdk = 36 // Or your target SDK version

    defaultConfig {
        applicationId = "com.example.schoolsystemapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    // CRITICAL: Enable Data Binding for connecting XML to ViewModel
    buildFeatures {
        dataBinding = true
    }

    // Set up Kotlin compatibility
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    // --- CORE AND UI ---
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // --- FRAGMENTS AND NAVIGATION (Essential for XML approach) ---
    // Fragment KTX for easy transaction/ViewModel access
    implementation("androidx.fragment:fragment-ktx:1.7.1")
    // Navigation Component - Fragment support
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    // Navigation Component - UI support
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")

    // --- ARCHITECTURE COMPONENTS (LifeCycle and ViewModel) ---
    // ViewModel utilities for Compose (optional, but good practice)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.0")
    // LiveData KTX (Essential for observing ViewModel LiveData in Fragments)
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.0")
    // If you need lifecycle-aware Coroutines
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.0")


    // --- HILT DEPENDENCY INJECTION ---
    implementation(libs.hilt.android.v2511) // Use your actual Hilt version
    kapt(libs.hilt.android.compiler) // Use your actual Hilt version

    // Hilt integration with Navigation/Fragments
    implementation("androidx.hilt:hilt-navigation-fragment:1.2.0")

    // --- COROUTINES (for async operations in Repository and ViewModel) ---
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // --- TESTING ---
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

// Apply kapt at the end for annotation processing
kapt {
    correctErrorTypes = true
}
