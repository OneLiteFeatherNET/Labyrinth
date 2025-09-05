plugins {
    id("java")
    alias(libs.plugins.run.paper)
    alias(libs.plugins.plugin.yml)
    alias(libs.plugins.shadow)

    `maven-publish`
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
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
    }

    runServer {
        minecraftVersion("1.20.6")
        jvmArgs("-Xmx2G", "-Dcom.mojang.eula.agree=true")
    }

    shadowJar {
        archiveClassifier.set("")
        archiveFileName.set("labyrinth.jar")
    }
}

publishing {
    publications.create<MavenPublication>("maven") {
        artifact(project.tasks.getByName("shadowJar"))
        version = rootProject.version as String
        artifactId = "labyrinth"
        groupId = rootProject.group as String
        pom {
            name = "Labyrinth"
            description = "Labyrinth zone protection for OneLiteFeather"
            url = "https://github.com/OneLiteFeatherNET/labyrinth"
            licenses {
                license {
                    name = "The Apache License, Version 2.0"
                    url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
                }
            }
            developers {
                developer {
                    id = "themeinerlp"
                    name = "Phillipp Glanz"
                    email = "p.glanz@madfix.me"
                }
            }
            scm {
                connection = "scm:git:git://github.com:OneLiteFeatherNET/Labyrinth.git"
                developerConnection = "scm:git:ssh://git@github.com:OneLiteFeatherNET/Labyrinth.git"
                url = "https://github.com/OneLiteFeatherNET/labyrinth"
            }
        }
    }

    repositories {
        maven {
            authentication {
                credentials(PasswordCredentials::class) {
                    // Those credentials need to be set under "Settings -> Secrets -> Actions" in your repository
                    username = System.getenv("ONELITEFEATHER_MAVEN_USERNAME")
                    password = System.getenv("ONELITEFEATHER_MAVEN_PASSWORD")
                }
            }

            name = "OneLiteFeatherRepository"
            val releasesRepoUrl = uri("https://repo.onelitefeather.dev/onelitefeather-releases")
            val snapshotsRepoUrl = uri("https://repo.onelitefeather.dev/onelitefeather-snapshots")
            url = if (version.toString().contains("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
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

