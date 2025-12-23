plugins {
    `kotlin-dsl`
}

group = "com.tk.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    //noinspection UseTomlInstead
    implementation("com.android.tools.build:gradle:8.13.1")
    //noinspection UseTomlInstead
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.2.21")
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "com.tk.buildlogic.android.application"
            implementationClass = "convention.AndroidApplicationConventionPlugin"
        }
        register("kotlin") {
            id = "com.tk.buildlogic.kotlin"
            implementationClass = "convention.KotlinAndroidConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "com.tk.buildlogic.android.library.compose"
            implementationClass = "convention.AndroidComposeConventionPlugin"
        }
        register("Hilt") {
            id = "com.tk.buildlogic.hilt"
            implementationClass = "convention.HiltConventionPlugin"
        }
        register("KotlinSerialization") {
            id = "com.tk.buildlogic.kotlin.serialization"
            implementationClass = "convention.KotlinSerializationConventionPlugin"
        }
        register("GoogleServices") {
            id = "com.tk.buildlogic.google.services"
            implementationClass = "convention.GoogleServicesConventionPlugin"
        }

        register("JacocoReport") {
            id = "com.tk.buildlogic.jacoco.report"
            implementationClass = "convention.JacocoAndroidConventionPlugin"
        }

        register("LintReport") {
            id = "com.tk.buildlogic.lint.report"
            implementationClass = "convention.AndroidLintConventionPlugin"
        }
    }
}

