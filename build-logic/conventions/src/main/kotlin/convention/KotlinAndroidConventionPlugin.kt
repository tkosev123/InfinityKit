package convention

import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import support.AppConfig

class KotlinAndroidConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {

        // Kotlin + Android
        pluginManager.apply("org.jetbrains.kotlin.android")

        // Kapt (if module needs annotation processing)
        pluginManager.apply("org.jetbrains.kotlin.kapt")

        // Android/Kotlin shared settings
        extensions.configure<BaseExtension> {
            compileSdkVersion(AppConfig.compileSdk)

            defaultConfig {
                minSdk = AppConfig.minSdk
            }

            compileOptions {
                sourceCompatibility = AppConfig.sourceCompatibility
                targetCompatibility = AppConfig.targetCompatibility
            }
        }

        // Kotlin compiler settings
        extensions.configure<KotlinAndroidProjectExtension> {
            compilerOptions {
                jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
                freeCompilerArgs.addAll(
                    "-opt-in=kotlin.RequiresOptIn",
                    "-Xjvm-default=all"
                )
            }
        }
    }
}