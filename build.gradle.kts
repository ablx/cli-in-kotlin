import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

plugins {
    kotlin("jvm") version "1.9.0"
    application
    id("com.github.johnrengelman.shadow") version "7.1.0"
}

group = "dev.verbosemode"
version = "1.0-SNAPSHOT"
val cliName = "dbCli"
repositories {
    mavenCentral()
}

tasks {
    withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
        archiveBaseName.set(cliName)
        archiveVersion.set(version.toString())
        archiveClassifier.set("")
        mergeServiceFiles()
    }
}

tasks.register("deployCli") {
    dependsOn("shadowJar")
    doLast {
        val jarPath = Paths.get("build/libs/$cliName-$version.jar")
        val hiddenFolderPath = Paths.get(System.getProperty("user.home"), ".yourHiddenFolder")
        val destinationJarPath = hiddenFolderPath.resolve(jarPath.fileName)

        // Create hidden folder if it doesn't exist
        if (!Files.exists(hiddenFolderPath)) {
            Files.createDirectories(hiddenFolderPath)
        }

        // Copy the fat jar to the hidden folder
        Files.copy(jarPath, destinationJarPath, StandardCopyOption.REPLACE_EXISTING)
        println("Jar copied to: $destinationJarPath")

        // Create the shell script
        val scriptContent = """
            #!/bin/bash
            java -jar $destinationJarPath "$@"
        """.trimIndent()

        val scriptPath = hiddenFolderPath.resolve("$cliName.sh")
        Files.write(scriptPath, scriptContent.toByteArray())
        scriptPath.toFile().setExecutable(true)
        println("Script created at: $scriptPath")

        // Create a symlink to the shell script
        val symlinkPath = Paths.get(System.getProperty("user.home"), cliName)
        if (Files.exists(symlinkPath)) {
            Files.delete(symlinkPath)
        }
        Files.createSymbolicLink(symlinkPath, scriptPath)
        println("Symlink created at: $symlinkPath")
    }
}


dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-cli:0.3.5")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("Parser")

}