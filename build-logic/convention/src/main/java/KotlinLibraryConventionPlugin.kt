import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import pl.inpost.buildlogic.convention.config.kotlinConfigPure
import pl.inpost.buildlogic.convention.implementation
import pl.inpost.buildlogic.convention.libs

class KotlinLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {

        with(target) {
            with(pluginManager) {
                apply("java-library")
                apply("org.jetbrains.kotlin.jvm")
            }
            kotlinConfigPure()
            dependencies {
                implementation(libs.findLibrary("inject-annotation").get())
            }
        }
    }
}