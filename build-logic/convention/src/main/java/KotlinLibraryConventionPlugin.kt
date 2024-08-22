import org.gradle.api.Plugin
import org.gradle.api.Project
import pl.inpost.buildlogic.convention.config.kotlinConfigPure

class KotlinLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {

        with(target) {
            with(pluginManager) {
                apply("java-library")
                apply("org.jetbrains.kotlin.jvm")
            }
            kotlinConfigPure()
        }
    }
}