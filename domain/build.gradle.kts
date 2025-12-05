plugins {
    alias(libs.plugins.android.library)
    id("com.tk.buildlogic.kotlin")
    id("com.tk.buildlogic.hilt")
}

android {
    namespace = "com.tk.domain"
}

dependencies {
    /* -----------Testing ----------- */
    implementation(libs.androidx.junit)
}