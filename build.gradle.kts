plugins {
    kotlin("jvm") version "1.9.23"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.microsoft.playwright:playwright:1.43.0")
    implementation(kotlin("stdlib-jdk8"))
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:1.5.31")
    testImplementation ("io.kotest:kotest-runner-junit5:+") // Example dependency version
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}