plugins {
    alias(libs.plugins.android.library)
    id("com.tk.buildlogic.kotlin")
}

android {
    namespace = "com.tk.mvi"

}

dependencies {
    //androidx-lifecycle-viewmodel-ktx
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.junit)
}