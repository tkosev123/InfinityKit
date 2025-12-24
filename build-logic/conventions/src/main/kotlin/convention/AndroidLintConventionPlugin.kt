package convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidLintConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        extensions.configure<CommonExtension<*, *, *, *, *, *>>("android") {
            lint {
                abortOnError = true
                warningsAsErrors = true
                checkReleaseBuilds = true
                baseline = file("$rootDir/lint-baseline.xml")
                disable.add("AndroidGradlePluginVersion")
            }
        }
    }
}