pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "VocalScribe"
include(":app")
include(":feature-home")
include(":feature-transcript")
include(":core-ui")
include(":speechrecognizer")
include(":models")
include(":data")
include(":feature-home-domain")
include(":feature-transcript-domain")
include(":core-domain")
