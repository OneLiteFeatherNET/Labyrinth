rootProject.name = "Labyrinth"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {

            version("paper", "1.20.6-R0.1-SNAPSHOT")
            version("plugin.yml", "0.6.0")
            version("run-paper", "3.0.2")
            version("publishdata", "1.4.0")
            version("shadow", "9.3.0")

            plugin("plugin.yml", "net.minecrell.plugin-yml.paper").versionRef("plugin.yml")
            plugin("run.paper", "xyz.jpenilla.run-paper").versionRef("run-paper")
            plugin("publishdata", "de.chojo.publishdata").versionRef("publishdata")
            plugin("shadow", "com.gradleup.shadow").versionRef("shadow")

            library("cloudPaper", "org.incendo", "cloud-paper").version("2.0.0-SNAPSHOT")
            library("cloudAnnotations", "org.incendo", "cloud-annotations").version("2.0.0")
            library("cloudBukkit", "org.incendo", "cloud-bukkit").version("2.0.0-SNAPSHOT")
            library("adventurePlatformBukkit", "net.kyori", "adventure-platform-bukkit").version("4.4.1")

            library("paper", "io.papermc.paper", "paper-api").versionRef("paper")

        }
    }
    repositories {
        mavenCentral()
        maven("https://central.sonatype.com/repository/maven-snapshots/")
        maven {
            name = "OneLiteFeatherRepository"
            url = uri("https://repo.onelitefeather.dev/onelitefeather")
            if (System.getenv("CI") != null) {
                credentials {
                    username = System.getenv("ONELITEFEATHER_MAVEN_USERNAME")
                    password = System.getenv("ONELITEFEATHER_MAVEN_PASSWORD")
                }
            } else {
                credentials(PasswordCredentials::class)
                authentication {
                    create<BasicAuthentication>("basic")
                }
            }
        }
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}