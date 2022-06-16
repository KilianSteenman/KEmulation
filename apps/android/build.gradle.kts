plugins {
    id("org.jetbrains.compose")
    id("com.android.application")
    kotlin("android")
}

group "com.kiliansteenman.kemulator"
version "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":emulators:chip-8"))
    implementation("androidx.compose.material:material:1.1.1")
    implementation("androidx.activity:activity-compose:1.4.0")
}

android {
    compileSdkVersion(31)
    defaultConfig {
        applicationId = "com.kiliansteenman.kemulator.android"
        minSdkVersion(24)
        targetSdkVersion(31)
        versionCode = 1
        versionName = "1.0-SNAPSHOT"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}