plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("maven-publish")
}

group = "com.purityvanilla"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
}

tasks.jar {
    manifest {
        attributes["paperweight-mappings-namespace"] = "spigot"
    }
}

tasks.shadowJar {
    manifest {
        attributes["paperweight-mappings-namespace"] = "spigot"
    }
}

val testServerPluginsPath: String by project
val localMavenRepoPath: String by project

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"]) // Publishing Java or Kotlin library
        }
    }
    repositories {
        maven {
            name = "local"
            url = uri(localMavenRepoPath) // Local Maven repo path
        }
    }
}