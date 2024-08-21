package pl.inpost.buildlogic.convention

import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.getByType

val Project.libs
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")


fun DependencyHandlerScope.implementation(dependency: Provider<MinimalExternalModuleDependency>): Dependency? {
    return add("implementation", dependency)
}

fun DependencyHandlerScope.ksp(dependency: Provider<MinimalExternalModuleDependency>): Dependency? {
    return add("ksp", dependency)
}

fun DependencyHandlerScope.api(dependency: Provider<MinimalExternalModuleDependency>): Dependency? {
    return add("api", dependency)
}

fun DependencyHandlerScope.androidTestImplementation(dependency: Provider<MinimalExternalModuleDependency>): Dependency? {
    return add("androidTestImplementation", dependency)
}

fun DependencyHandlerScope.debugImplementation(dependency: Provider<MinimalExternalModuleDependency>): Dependency? {
    return add("debugImplementation", dependency)
}