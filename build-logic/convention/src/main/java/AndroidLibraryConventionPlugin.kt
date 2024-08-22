import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import pl.inpost.buildlogic.convention.config.kotlinConfig
import pl.inpost.buildlogic.convention.libs
import pl.inpost.buildlogic.convention.pluginId

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {

        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("androidLibrary").pluginId)
                apply(libs.findPlugin("kotlinAndroid").pluginId)
                apply(libs.findPlugin("inpost-hilt").pluginId)
                apply(libs.findPlugin("inpost-room").pluginId)
                apply(libs.findPlugin("inpost-android-library-compose").pluginId)
            }

            extensions.configure<LibraryExtension> {
                kotlinConfig(this)
                defaultConfig {
                    minSdk = libs.findVersion("minSdk").get().displayName.toInt()
                    compileSdk = libs.findVersion("compileSdk").get().displayName.toInt()

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    consumerProguardFiles("consumer-rules.pro")
                }

            }
        }
    }
}