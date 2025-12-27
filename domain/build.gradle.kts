plugins {
    alias(libs.plugins.android.library)
    id("com.tk.buildlogic.kotlin")
    id("com.tk.buildlogic.hilt")
    id("com.tk.buildlogic.lint.report")
    id("com.tk.buildlogic.jacoco.report")
}

android {
    namespace = "com.tk.domain"
}

dependencies {
    /* -----------Testing ----------- */
    testImplementation(libs.bundles.unittest)
}