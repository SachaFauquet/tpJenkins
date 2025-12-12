plugins {
    id("java")
    id("application")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

application {
    mainClass.set("org.example.Main")
}

tasks.test {
    useJUnitPlatform()

    // Configuration pour les rapports de tests
    testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = true
    }
}

// Configuration pour générer un JAR exécutable
tasks.jar {
    manifest {
        attributes(
            "Main-Class" to "org.example.Main",
            "Implementation-Title" to "JavaJenkins Factorielle",
            "Implementation-Version" to project.version
        )
    }

    // Créer un fat JAR avec toutes les dépendances
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}