package convention

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import support.AppConfig

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {

        pluginManager.apply("com.android.application")

        extensions.configure<ApplicationExtension> {
            compileSdk = AppConfig.compileSdk

            defaultConfig {
                applicationId = AppConfig.applicationId
                minSdk = AppConfig.minSdk
                targetSdk = AppConfig.targetSdk
                versionCode = AppConfig.versionCode
                versionName = AppConfig.versionName
            }

            buildTypes {
                getByName("debug") {
                    isMinifyEnabled = false
                }
                getByName("release") {
                    isMinifyEnabled = true
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                }
            }

            buildFeatures {
                compose = true
            }
        }
    }
}
