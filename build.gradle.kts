import groovy.xml.dom.DOMCategory.attributes

plugins {
    kotlin("jvm") version "1.9.0"
    application
}

group = "dev.verbosemode"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
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