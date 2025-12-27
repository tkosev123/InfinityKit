plugins {
    alias(libs.plugins.android.library)
    id("com.tk.buildlogic.kotlin")
    id("com.tk.buildlogic.hilt")
    id("com.tk.buildlogic.lint.report")
}

android {
    namespace = "com.tk.di"
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)

    /* -----------Testing ----------- */
    implementation(libs.androidx.junit)
}