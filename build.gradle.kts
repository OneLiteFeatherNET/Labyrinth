import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    id("java")
    id("net.minecrell.plugin-yml.paper") version "0.6.0"
    id("xyz.jpenilla.run-paper") version "2.2.3"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    `maven-publish`
    alias(libs.plugins.publishdata)
}

group = "net.onelitefeather"
version = "0.0.1" // Change

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
    implementation("org.incendo:cloud-paper:2.0.0-beta.2")
    implementation("org.incendo:cloud-annotations:2.0.0-beta.2")
    implementation("org.incendo:cloud-bukkit:2.0.0-beta.2")
}
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks {
    compileJava {
        options.release.set(17)
        options.encoding = "UTF-8"
    }

    runServer {
        minecraftVersion("1.20.4")
        jvmArgs("-Xmx2G", "-Dcom.mojang.eula.agree=true")
    }
}

publishData {
    addBuildData()
    useGitlabReposForProject("284", "https://gitlab.themeinerlp.dev/")
    publishTask("shadowJar")
}

publishing {
    publications.create<MavenPublication>("maven") {
        // configure the publication as defined previously.
        publishData.configurePublication(this)
        version = publishData.getVersion(false)
    }

    repositories {
        maven {
            credentials(HttpHeaderCredentials::class) {
                name = "Job-Token"
                value = System.getenv("CI_JOB_TOKEN")
            }
            authentication {
                create("header", HttpHeaderAuthentication::class)
            }


            name = "Gitlab"
            // Get the detected repository from the publish data
            url = uri(publishData.getRepository())
        }
    }
}

paper {

    main = "net.onelitefeather.labyrinth.Labyrinth"
    name = "Labyrinth"
    version = publishData.getVersion(true)
    description = "This is a prototype plugin for the Labyrinth of our Survival Server"
    website = "https://discord.onelitefeather.net"
    author = "OneLiteFeather"
    apiVersion = "1.20"

    hasOpenClassloader = false
    generateLibrariesJson = false
    foliaSupported = false

    permissions {
        register("labyrinth.setup.center") {
            default = BukkitPluginDescription.Permission.Default.TRUE
        }
        register("labyrinth.setup.setradius") {
            default = BukkitPluginDescription.Permission.Default.TRUE
        }

    }
}

