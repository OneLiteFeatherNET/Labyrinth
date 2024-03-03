import org.gradle.internal.impldep.junit.runner.Version.id

plugins {
    id("java")
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
    id("xyz.jpenilla.run-paper") version "2.2.2"
}

group = "net.onelitefeather"
version = "0.0.1-SNAPSHOT" // Change

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
    implementation("org.incendo:cloud-paper:2.0.0-beta.2")
    implementation("org.incendo:cloud-annotations:2.0.0-beta.2")
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
    }
}
bukkit {
    main = "net.onelitefeather.labyrinth.Labyrinth"
    name = "Labyrinth"
    version = "0.0.1-Snapshot"
    description = "This is a prototype plugin for the Labyrinth of our Survival Server"
    website = "https://discord.onelitefeather.net"
    author = "OneLiteFeather"
    apiVersion = "1.20"
    prefix = "Labyrinth"

    commands {
        register("center") {
            description = "Save the center (middle point) of your cycle"
            permissionMessage = "You do not have permission to setup a cycle"
            permission = "labyrinth.setup.center"
            usage = "/labyrinth center"
        }
        register("setradius") {
            description = "Save the second position needed for the cycle radius"
            permissionMessage = "You do not have permission to setup the cycle radius!"
            permission = "labyrinth.setup.setradius"
            usage = "/labyrinth setradius"
        }
    }
}

