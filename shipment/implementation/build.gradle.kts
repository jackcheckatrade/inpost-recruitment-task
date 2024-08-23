plugins {
    alias(libs.plugins.inpost.android.library)
}

android {
    namespace = "pl.inpost.shipment.implementation"

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(project(":core:common"))
    implementation(project(":core:android-common"))
    implementation(project(":core:design-system"))
    implementation(project(":shipment:api"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)



}
