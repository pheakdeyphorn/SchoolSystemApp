plugins {
    // 1. Standard Android Application plugin
    id("com.android.application")

    // 2. Kotlin Android plugin
    kotlin("android")

    // 3. Kapt (Kotlin Annotation Processing Tool) for Hilt
    kotlin("kapt")

    // 4. Hilt Gradle Plugin for dependency injection setup
    alias(libs.plugins.hilt)

    // 5. Google Services plugin for Firebase configuration
    alias(libs.plugins.google.gms.google.services)
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
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)

    // --- NAVIGATION COMPONENT ---
    // Fragment KTX provides extension functions for Fragments
    implementation(libs.androidx.fragment.ktx)
    // Safe Args plugin should be used if you need type-safe argument passing
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // --- LIFECYCLE, LIVEDATA, AND VIEWMODEL ---
    // ViewModel KTX provides the viewModels() delegate
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    // LiveData KTX (for observing ViewModel LiveData in Fragments)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    // If you need lifecycle-aware Coroutines
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // --- HILT DEPENDENCY INJECTION ---
    // Hilt core dependency
    implementation(libs.hilt.android)
    // Hilt annotation processor (use kapt with Hilt)
    kapt(libs.hilt.compiler)

    // Hilt integration with Navigation/Fragments
    implementation(libs.androidx.hilt.navigation.fragment)

    // --- COROUTINES (for async operations in Repository and ViewModel) ---
    // Core coroutine libraries
    implementation(libs.kotlinx.coroutines.core)
    // Android-specific coroutine extensions
    implementation(libs.kotlinx.coroutines.android)

    // --- FIREBASE DEPENDENCIES ---

    // 1. FIREBASE PLATFORM: Manages all Firebase versions consistently
    // This assumes you defined a firebase-bom version in your libs.versions.toml
    implementation(platform(libs.firebase.bom))

    // 2. FIREBASE AUTHENTICATION DEPENDENCY
    implementation(libs.firebase.auth)

    // 3. CLOUD FIRESTORE DEPENDENCY (The Database)
    implementation(libs.firebase.firestore)

    // 4. GOOGLE SIGN-IN DEPENDENCY (for the Google Button)
    // This is required for integrating Google Sign-In, often used with Firebase Auth
    implementation(libs.play.services.auth)


    // --- TESTING ---
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Hilt Android Testing
    androidTestImplementation(libs.hilt.android.testing)
    kaptAndroidTest(libs.hilt.compiler)
}

// Apply kapt at the end for annotation processing
kapt {
    correctErrorTypes = true
}
