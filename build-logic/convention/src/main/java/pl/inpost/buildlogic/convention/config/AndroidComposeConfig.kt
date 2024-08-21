package pl.inpost.buildlogic.convention.config

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import pl.inpost.buildlogic.convention.androidTestImplementation
import pl.inpost.buildlogic.convention.debugImplementation
import pl.inpost.buildlogic.convention.implementation
import pl.inpost.buildlogic.convention.libs

internal fun Project.androidComposeConfig(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        dependencies {
            val composeBom = libs.findLibrary("androidx-compose-bom").get()

            implementation(platform(composeBom))
            androidTestImplementation(platform(composeBom))

            implementation(libs.findLibrary("androidx-material3").get())
            implementation(libs.findLibrary("androidx-activity-compose").get())
            implementation(libs.findLibrary("androidx-ui-viewModel").get())


            implementation(libs.findLibrary("androidx-ui-tooling-preview").get())
            debugImplementation(libs.findLibrary("androidx-ui-tooling").get())
        }
    }
}