plugins {
    alias(libs.plugins.android.library)
    id("com.tk.buildlogic.kotlin")
    id("com.tk.buildlogic.hilt")
}

android {
    namespace = "com.tk.data"
}

dependencies {
    implementation(project(":domain"))
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)

    /* -----------Testing ----------- */
    implementation(libs.androidx.junit)
}