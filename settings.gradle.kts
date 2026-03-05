pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // This address is required for the scanner library
        maven("https://jitpack.io")
    }
}
rootProject.name = "AttendanceApp"
include(":app")
