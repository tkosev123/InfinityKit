package support

import org.gradle.api.JavaVersion

object AppConfig {
    const val compileSdk = 36
    const val minSdk = 24
    const val targetSdk = 36
    val sourceCompatibility = JavaVersion.VERSION_17
    val targetCompatibility = JavaVersion.VERSION_17

    val versionCode = 1

    val versionName = "1.0"
    val applicationId = "com.tk.infinitykit"

}