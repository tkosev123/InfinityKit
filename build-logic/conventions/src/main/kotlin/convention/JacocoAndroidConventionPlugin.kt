package convention

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.*
import org.gradle.testing.jacoco.tasks.JacocoCoverageVerification
import org.gradle.testing.jacoco.tasks.JacocoReport

class JacocoAndroidConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) = with(project) {
        plugins.apply("jacoco")

        afterEvaluate {

            val debugTestTask = "testDebugUnitTest"

            // ----------------------------
            // Jacoco Report
            // ----------------------------
            tasks.register<JacocoReport>("jacocoDebugReport") {
                dependsOn("testDebugUnitTest")

                reports {
                    xml.required.set(true)
                    html.required.set(true)
                }

                classDirectories.from(
                    fileTree(layout.buildDirectory.dir("tmp/kotlin-classes/debug")) {
                        exclude(
                            "**/R.class",
                            "**/R$*.class",
                            "**/BuildConfig.*",
                            "**/Manifest*.*",
                            "**/*Test*.*",
                            "android/**/*.*"
                        )
                    }
                )

                sourceDirectories.from(
                    files("src/main/java", "src/main/kotlin")
                )

                executionData.from(
                    layout.buildDirectory.file("jacoco/testDebugUnitTest.exec")
                )
            }

            // ----------------------------
            // Jacoco Coverage Verification
            // ----------------------------
            tasks.register<JacocoCoverageVerification>("jacocoDebugCoverageVerification") {
                dependsOn("jacocoDebugReport")

                classDirectories.from(
                    fileTree(layout.buildDirectory.dir("tmp/kotlin-classes/debug"))
                )

                sourceDirectories.from(
                    files("src/main/java", "src/main/kotlin")
                )

                executionData.from(
                    layout.buildDirectory.file("jacoco/testDebugUnitTest.exec")
                )

               // violationRules {
               //     rule {
               //         limit {
               //             counter = "LINE"
               //             minimum = "0.80".toBigDecimal()
               //         }
               //     }
               // }
            }

            tasks.register("jacocoDebugCheck") {
                dependsOn("jacocoDebugReport", "jacocoDebugCoverageVerification")
            }
        }
    }
}