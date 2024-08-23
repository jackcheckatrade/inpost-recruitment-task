import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import pl.inpost.buildlogic.convention.implementation
import pl.inpost.buildlogic.convention.ksp
import pl.inpost.buildlogic.convention.libs

class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.google.devtools.ksp")
            dependencies {
                ksp(libs.findLibrary("hilt.compiler").get())
            }

            pluginManager.withPlugin("com.android.base") {
                pluginManager.apply("com.google.dagger.hilt.android")
                dependencies {
                    implementation(libs.findLibrary("hilt.android").get())
                }
            }
        }
    }
}