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
    compileOnly("io.papermc.paper", "paper-api", "1.21.1-R0.1-SNAPSHOT")
    implementation("org.spongepowered", "configurate-yaml", "4.0.0")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.jar {
    manifest {
        attributes["paperweight-mappings-namespace"] = "spigot"
    }
}

tasks.shadowJar {
    dependsOn(tasks.build)
    archiveClassifier.set("") // This removes the default "-all" classifier
    archiveFileName.set("pvLib.jar")

    manifest {
        attributes["paperweight-mappings-namespace"] = "spigot"
    }
}

val testServerPluginsPath: String by project
tasks {
    val copyToServer by creating(Copy::class) {
        dependsOn("shadowJar")
        from(layout.buildDirectory.file("libs"))
        include("pvLib.jar")
        into(file(testServerPluginsPath)) // Use the externalized path here
    }

    /* Step to run copyToServer after build
    build {
        finalizedBy(copyToServer)
    }
     */
}

val localMavenRepoPath: String by project
publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifact(tasks.shadowJar.get()) {
                classifier = "" // Ensure this is different or empty if not using a classifier
            }
        }
    }
    repositories {
        maven {
            name = "local"
            url = uri(localMavenRepoPath) // Local Maven repo path
        }
    }
}