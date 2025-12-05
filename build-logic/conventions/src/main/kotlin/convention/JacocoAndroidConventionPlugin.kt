    package convention

    import org.gradle.api.Plugin
    import org.gradle.api.Project
    import org.gradle.kotlin.dsl.*
    import org.gradle.testing.jacoco.tasks.JacocoReport

    class JacocoAndroidConventionPlugin : Plugin<Project> {
        override fun apply(project: Project) {
            project.plugins.apply("jacoco")

            project.afterEvaluate {
                project.tasks.register("jacocoDebugReport", JacocoReport::class) {
                    dependsOn("testDebugUnitTest")

                    reports {
                        xml.required.set(true)
                        html.required.set(true)
                    }

                    val kotlinClasses = fileTree("${project.buildDir}/tmp/kotlin-classes/debug")

                    classDirectories.setFrom(kotlinClasses)
                    sourceDirectories.setFrom(
                        project.files("src/main/java", "src/main/kotlin")
                    )

                    executionData.setFrom(
                        project.fileTree(project.layout.buildDirectory) {
                            include("**/*.exec", "**/*.ec")
                        }
                    )
                }
            }
        }
    }