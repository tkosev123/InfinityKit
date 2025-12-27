plugins {
    alias(libs.plugins.android.library)
    id("com.tk.buildlogic.kotlin")
    id("com.tk.buildlogic.hilt")
    id("com.tk.buildlogic.lint.report")
    id("com.tk.buildlogic.jacoco.report")
}

android {
    namespace = "com.tk.data"
}

dependencies {
    /* ----------- Modules ----------- */
    implementation(project(":domain"))

    /* ----------- Dependencies ----------- */
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)

    /* -----------Testing ----------- */
    testImplementation(libs.bundles.unittest)
}