plugins {
    kotlin("multiplatform")
    id("com.android.application")
    id("org.jetbrains.compose")
}

kotlin {
    android()
    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(project(":apps:common"))
            }
        }
    }
}

//dependencies {
//    implementation(project(":apps:common"))
//    implementation(project(":emulators:chip-8"))
//    implementation("androidx.appcompat:appcompat:1.6.1")
//    implementation("androidx.compose.material:material:1.4.3")
//    implementation("androidx.activity:activity-compose:1.7.2")
//}

android {
    namespace = "com.kiliansteenman.kemulation"

    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    defaultConfig {
        applicationId = "com.kiliansteenman.kemulator.android"
        minSdk = (findProperty("android.minSdk") as String).toInt()
        targetSdk = (findProperty("android.targetSdk") as String).toInt()
        versionCode = 1
        versionName = "1.0.0"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}