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

            version("paper", "1.20.6-R0.1-SNAPSHOT")
            version("plugin.yml", "0.6.0")
            version("run-paper", "2.3.0")
            version("publishdata", "1.4.0")
            version("shadow", "8.3.0")

            plugin("plugin.yml", "net.minecrell.plugin-yml.paper").versionRef("plugin.yml")
            plugin("run.paper", "xyz.jpenilla.run-paper").versionRef("run-paper")
            plugin("publishdata", "de.chojo.publishdata").versionRef("publishdata")
            plugin("shadow", "com.gradleup.shadow").versionRef("shadow")

            library("cloudPaper", "org.incendo", "cloud-paper").version("2.0.0-beta.9")
            library("cloudAnnotations", "org.incendo", "cloud-annotations").version("2.0.0-rc.2")
            library("cloudBukkit", "org.incendo", "cloud-bukkit").version("2.0.0-SNAPSHOT")
            library("adventurePlatformBukkit", "net.kyori", "adventure-platform-bukkit").version("4.3.2")

            library("paper", "io.papermc.paper", "paper-api").versionRef("paper")

        }
    }
}