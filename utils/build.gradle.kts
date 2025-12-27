plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.tk.utils"

}

dependencies {
    /* ----------- Testing ----------- */
    implementation(libs.androidx.junit)
    implementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
}