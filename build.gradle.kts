plugins {
    java
    `maven-publish`
    alias(libs.plugins.run.paper)
    alias(libs.plugins.plugin.yml)
    alias(libs.plugins.shadow)
}

group = "net.onelitefeather"
version = "1.2.10" // x-release-please-version
description = "Labyrinth zone protection for OneLiteFeather - Prototype"

dependencies {
    implementation(libs.cloudPaper)
    implementation(libs.cloudAnnotations)
    implementation(libs.cloudBukkit)
    implementation(libs.adventurePlatformBukkit)
    implementation(libs.paper)

    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.platform.launcher)
    testImplementation(libs.mockbukkit)

    testRuntimeOnly(libs.junit.jupiter.engine)
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
    }

    runServer {
        minecraftVersion("1.21.11")
        jvmArgs("-Xmx2G", "-Dcom.mojang.eula.agree=true")
    }

    shadowJar {
        archiveClassifier.set("")
        archiveFileName.set("labyrinth.jar")
    }

    test {
        useJUnitPlatform()
        jvmArgs("-Dlabyrinth.insideTest=true")
        testLogging {
            events("passed", "skipped", "failed")
        }
    }
}

paper {
    main = "net.onelitefeather.labyrinth.Labyrinth"
    name = "Labyrinth"
    description = "This is a prototype plugin for the Labyrinth of our Survival Server"
    website = "https://discord.onelitefeather.net"
    author = "OneLiteFeather"
    apiVersion = "1.20"

    hasOpenClassloader = false
    generateLibrariesJson = false
    foliaSupported = false

    permissions {
        register("labyrinth.setup.center") {
            description = "This permission is needed to create the center of the zone"
        }
        register("labyrinth.setup.setradius") {
            description = "This permission is needed to set the radius of the zone."
        }
        register("labyrinth.toggle.mobspawn") {
            description = "This permission is needed to toggle mobspawning for the zone."
        }
        register("labyrinth.setup.createzone") {
            description = "This permission is needed to create a new zone entry."
        }
        register("labyrinth.setup.deletezone") {
            description = "This permission is needed to delete the zone."
        }
    }
}
