rootProject.name = "KEmulation"

include(
    "emulators:gameboy",
    "emulators:chip-8",
    "apps:android",
    "apps:common",
    "apps:desktop",
    "apps:web",
)

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
