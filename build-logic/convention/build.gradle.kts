import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "pl.inpost.buildlogic.convention"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.ksp.gradlePlugin)
    implementation(libs.compose.gradlePlugin)
    implementation(libs.androidx.room.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "inpost.gradleplugin.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("androidLibrary") {
            id = "inpost.gradleplugin.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("kotlinLibrary") {
            id = "inpost.gradleplugin.kotlin.library"
            implementationClass = "KotlinLibraryConventionPlugin"
        }

        register("androidComposeLibrary") {
            id = "inpost.gradleplugin.android.library.compose"
            implementationClass = "AndroidComposeLibraryConventionPlugin"
        }

        register("androidComposeApplication") {
            id = "inpost.gradleplugin.android.application.compose"
            implementationClass = "AndroidComposeApplicationConventionPlugin"
        }

        register("androidRoom") {
            id = "inpost.gradleplugin.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }

        register("androidHilt") {
            id = "inpost.gradleplugin.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
    }
}