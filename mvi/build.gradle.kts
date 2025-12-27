plugins {
    alias(libs.plugins.android.library)
    id("com.tk.buildlogic.kotlin")
    id("com.tk.buildlogic.android.library.compose")
    alias(libs.plugins.compose.compiler)
    id("com.tk.buildlogic.lint.report")
    id("com.tk.buildlogic.jacoco.report")
}

android {
    namespace = "com.tk.mvi"
}

dependencies {
    /* ----------- Dependencies ----------- */
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    /* -----------Testing ----------- */
    testImplementation(libs.bundles.unittest)
}