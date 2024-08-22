import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import pl.inpost.buildlogic.convention.config.kotlinConfig
import pl.inpost.buildlogic.convention.libs
import pl.inpost.buildlogic.convention.pluginId

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val applicationId = "pl.inpost.recruitmenttask"

            with(pluginManager) {
                apply(libs.findPlugin("androidApplication").pluginId)
                apply(libs.findPlugin("kotlinAndroid").pluginId)
                apply(libs.findPlugin("inpost-android-application-compose").pluginId)
                apply(libs.findPlugin("ksp").pluginId)
                apply(libs.findPlugin("inpost-hilt").pluginId)
                apply(libs.findPlugin("inpost-room").pluginId)
            }

            extensions.configure<ApplicationExtension> {
                namespace = applicationId
                defaultConfig {
                    this.applicationId = applicationId
                    targetSdk = libs.findVersion("targetSdk").get().displayName.toInt()
                }
                kotlinConfig(this)
            }

        }
    }
}