import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.32"
    application
    kotlin("kapt") version "1.4.32"
}

group = "me.hachem"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://dl.bintray.com/kotlin/kotlinx/")
}

dependencies {
    implementation("org.jetbrains.kotlinx", "kotlinx-cli", "0.2")
    implementation("joda-time:joda-time:2.9.9")

    testImplementation("junit:junit:4.+")


}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}


application {
    mainClassName = "MainKt"
}

val jar by tasks.getting(Jar::class) {
    manifest {
        attributes["Main-Class"] = "MainKt"
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }) {
        exclude("META-INF/*.RSA", "META-INF/*.SF", "META-INF/*.DSA")
    }
}