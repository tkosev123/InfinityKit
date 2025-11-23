package convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import support.AppConfig

class AndroidComposeConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        val libsCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")
        val hasApplicationExtension = extensions.findByType(ApplicationExtension::class.java) != null
        val hasLibraryExtension = extensions.findByType(LibraryExtension::class.java) != null
        
        if (!hasApplicationExtension && !hasLibraryExtension) {
            pluginManager.apply("com.android.library")
        }

        afterEvaluate {
            val androidExtension = extensions.findByType(ApplicationExtension::class.java)
                ?: extensions.findByType(LibraryExtension::class.java)
                ?: error("AndroidComposeConventionPlugin: No Android extension found after applying plugins")

            androidExtension.apply {
                buildFeatures.compose = true
            }
        }

        dependencies {
            add("implementation", platform(libsCatalog.findLibrary("androidx-compose-bom").get()))
            add("implementation", libsCatalog.findLibrary("androidx-ui").get())
            add("implementation", libsCatalog.findLibrary("androidx-material3").get())
            add("implementation", libsCatalog.findLibrary("androidx-ui-tooling-preview").get())
            add("implementation", libsCatalog.findLibrary("androidx-activity-compose").get())
            add("debugImplementation", libsCatalog.findLibrary("androidx-ui-tooling").get())
        }
    }
}