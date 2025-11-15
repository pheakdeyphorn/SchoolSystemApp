// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // Android App Plugin - Applied to the application module
    alias(libs.plugins.android.application) apply false
    // Kotlin Android Plugin - Applied to Kotlin modules
    alias(libs.plugins.kotlin.android) apply false
    // Kotlin Compose Plugin - Kept in case you use Compose later
    alias(libs.plugins.kotlin.compose) apply false

    // KSP (Kotlin Symbol Processing) Plugin - Required by Hilt, applied to modules
    alias(libs.plugins.ksp) apply false
    // Hilt Plugin - Applied to the application module
    alias(libs.plugins.hilt) apply false

    // Google Services Plugin - Required for Firebase, applied to the application module
    alias(libs.plugins.google.gms.google.services) apply false
}