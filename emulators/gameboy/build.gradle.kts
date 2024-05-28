plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

group = "com.kiliansteenman.kemulation.gameboy"
version = "0.0.1"

repositories {
    mavenCentral()
}
kotlin {
    jvm()
}