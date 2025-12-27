package convention

import org.gradle.api.Plugin
import org.gradle.api.Project

class GoogleServicesConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        val googleServicesFile = file("google-services.json")

        if (googleServicesFile.exists()) {
            pluginManager.apply("com.google.gms.google-services")
        } else {
            logger.lifecycle(
                "Google Services plugin NOT applied (google-services.json missing)"
            )
        }
    }
}

