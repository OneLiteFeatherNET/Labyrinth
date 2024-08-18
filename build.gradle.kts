plugins {
    id("java")
    alias(libs.plugins.run.paper)
    alias(libs.plugins.plugin.yml)
    alias(libs.plugins.shadow)
    alias(libs.plugins.publishdata)

    `maven-publish`
}

group = "net.onelitefeather"
version = "0.1.0" // Change

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation(libs.cloudPaper)
    implementation(libs.cloudAnnotations)
    implementation(libs.cloudBukkit)
    implementation(libs.adventurePlatformBukkit)
    implementation(libs.paper)

}
java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks {
    compileJava {
        options.release.set(21)
        options.encoding = "UTF-8"
    }

    runServer {
        minecraftVersion("1.20.6")
        jvmArgs("-Xmx2G", "-Dcom.mojang.eula.agree=true")
    }
}

publishData {
    addBuildData()
    useGitlabReposForProject("284", "https://onelitefeather.dev/")
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

    }
}

