import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import pl.inpost.buildlogic.convention.androidTestImplementation
import pl.inpost.buildlogic.convention.config.kotlinConfig
import pl.inpost.buildlogic.convention.implementation
import pl.inpost.buildlogic.convention.ksp
import pl.inpost.buildlogic.convention.libs
import pl.inpost.buildlogic.convention.pluginId
import pl.inpost.buildlogic.convention.testImplementation

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

            dependencies {
                implementation(libs.findLibrary("androidx.core.ktx").get())
                implementation(libs.findLibrary("androidx.appcompat").get())

                implementation(libs.findLibrary("moshi.kotlin").get())
                ksp(libs.findLibrary("moshi.kotlin.codegen").get())

                testImplementation(libs.findLibrary("junit").get())
                androidTestImplementation(libs.findLibrary("androidx.junit").get())
                androidTestImplementation(libs.findLibrary("androidx.espresso.core").get())
                testImplementation(libs.findLibrary("android-test-mockk").get())
                testImplementation(libs.findLibrary("coroutine-test").get())
                testImplementation(libs.findLibrary("turbine").get())
            }

        }
    }
}