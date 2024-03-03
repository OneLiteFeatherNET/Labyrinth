rootProject.name = "Labyrinth"

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://eldonexus.de/repository/maven-public/")
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            version("publishdata", "1.2.5-DEV")
            plugin("publishdata", "de.chojo.publishdata").versionRef("publishdata")
        }
    }
}