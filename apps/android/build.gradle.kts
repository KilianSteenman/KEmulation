plugins {
    id("org.jetbrains.compose")
    id("com.android.application")
    kotlin("android")
}

group "com.kiliansteenman.kemulation"
version "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":apps:common"))
    implementation(project(":emulators:chip-8"))
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("androidx.compose.material:material:1.2.1")
    implementation("androidx.activity:activity-compose:1.5.1")
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