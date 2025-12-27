plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.tk.utils"

}

dependencies {
    /* ----------- Testing ----------- */
    testImplementation(libs.androidx.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
}