plugins {
    id("com.tk.buildlogic.android.application")
    id("com.tk.buildlogic.kotlin")
    id("com.tk.buildlogic.hilt")
    id("com.tk.buildlogic.google.services")
    id("com.tk.buildlogic.android.library.compose")
    id("com.tk.buildlogic.kotlin.serialization")
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.tk.infinitykit"
}

dependencies {
    implementation(project(":di"))
    implementation(project(":domain"))
    implementation(project(":mvi"))

    implementation(libs.core.splashscreen)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.material)
    implementation(libs.androidx.material3)
    implementation(libs.kotlinx.serialization.json)

    /* -----------Testing ----------- */
    implementation(libs.androidx.junit)
}