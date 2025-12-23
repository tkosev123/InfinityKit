plugins {
    alias(libs.plugins.android.library)
    id("com.tk.buildlogic.kotlin")
    id("com.tk.buildlogic.android.library.compose")
    alias(libs.plugins.compose.compiler)
    id("com.tk.buildlogic.lint.report")

}

android {
    namespace = "com.tk.mvi"
}

dependencies {
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.junit)
}