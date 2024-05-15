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
            version("publishdata", "1.4.0")
            plugin("publishdata", "de.chojo.publishdata").versionRef("publishdata")
        }
    }
}