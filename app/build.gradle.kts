plugins {
    alias(libs.plugins.inpost.android.application)
}

android {
    defaultConfig {
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(project(":core:android-common"))
    implementation(project(":core:design-system"))
    implementation(project(":core:common"))
    implementation(project(":shipment:implementation"))
    implementation(project(":shipment:api"))
}