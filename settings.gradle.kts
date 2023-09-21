rootProject.name = "MyApplication"

include(":androidApp")
include(":shared")
include(":desktopApp")

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
    }

    plugins {
        val kotlinVersion = extra["kotlin.version"] as String
        val agpVersion = extra["agp.version"] as String
        val composeVersion = extra["compose.version"] as String

        kotlin("jvm").version(kotlinVersion)
        kotlin("multiplatform").version(kotlinVersion)
        kotlin("android").version(kotlinVersion)
        kotlin("plugin.serialization").version(kotlinVersion)

        id("com.android.base").version(agpVersion)
        id("com.android.application").version(agpVersion)
        id("com.android.library").version(agpVersion)

        id("org.jetbrains.compose").version(composeVersion)
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
    versionCatalogs {
        create("libs") {
            version("kamel-image-version", "0.6.0")
            version("ktor-version", "2.3.1")
            version("kotlinx-serialization-version", "1.6.0")
            version("kotlinx-coroutines-version", "1.6.0")
            version("moko-mvvm-version", "0.16.1")
            version("activity-compose-version", "1.6.1")
            version("appcompat-version ", "1.6.1")
            version("core-ktx-version", "1.9.0")
            version("android-gradle-plugin-version", "7.4.2")

            library("kamel-image", "media.kamel", "kamel-image").versionRef("kamel-image-version")
            library("ktor-client-core", "io.ktor", "ktor-client-core").versionRef("ktor-version")
            library("ktor-client-content-negotiation", "io.ktor", "ktor-client-content-negotiation").versionRef("ktor-version")
            library("ktor-serialzation-json", "io.ktor", "ktor-serialization-kotlinx-json").versionRef("ktor-version")
            library("ktor-client-android", "io.ktor", "ktor-client-android").versionRef("ktor-version")
            library("ktor-client-darwin", "io.ktor", "ktor-client-darwin").versionRef("ktor-version")
            library("ktor-client-okhttp", "io.ktor", "ktor-client-okhttp").versionRef("ktor-version")
            library("kotlinx-serialization-json", "org.jetbrains.kotlinx", "kotlinx-serialization-json").versionRef("kotlinx-serialization-version")
            library("kotlinx-coroutines-swing", "org.jetbrains.kotlinx", "kotlinx-coroutines-swing").versionRef("kotlinx-coroutines-version")
            library("moko-mvvm-core", "dev.icerock.moko", "mvvm-core").versionRef("moko-mvvm-version")
            library("moko-mvvm-compose", "dev.icerock.moko", "mvvm-compose").versionRef("moko-mvvm-version")
            library("androidx-activity-compose", "androidx.activity", "activity-compose").versionRef("activity-compose-version")
            library("androidx-appcompat", "androidx.appcompat", "appcompat").versionRef("appcompat-version")
            library("androidx-core", "androidx.core", "core-ktx").versionRef("core-ktx-version")
        }
    }
}
