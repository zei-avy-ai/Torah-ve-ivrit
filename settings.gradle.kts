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
    }
}

rootProject.name = "TorahVeIvrit"

// Indique à Gradle d'aller chercher dans le sous-dossier app/app
include(":app")
project(":app").projectDir = file("app/app")
