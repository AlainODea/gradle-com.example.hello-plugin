import groovy.lang.GroovyObject
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jfrog.gradle.plugin.artifactory.dsl.PublisherConfig

buildscript {
    repositories {
        jcenter()
    }
}

plugins {
    `java-gradle-plugin`
    `maven-publish`
    `kotlin-dsl`
    id("com.jfrog.artifactory") version "4.9.0"
    kotlin("jvm") version "1.3.11"
    id("io.spring.dependency-management") version "1.0.6.RELEASE"
}

group = "com.example.hello"
version = "0.1-SNAPSHOT"

gradlePlugin {
    plugins {
        create("helloPlugin") {
            id = "com.example.hello"
            implementationClass = "com.example.HelloPlugin"
        }
    }
}
repositories {
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom("org.junit:junit-bom:5.3.2")
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit:junit-bom:latest.release")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("com.natpryce:hamkrest:1.7.0.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks {
    withType<JavaExec> {
        jvmArgs = listOf("-noverify", "-XX:TieredStopAtLevel=1")
    }

    withType<KotlinCompile> {
        val javaVersion = JavaVersion.VERSION_1_8.toString()
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
        kotlinOptions {
            apiVersion = "1.3"
            javaParameters = true
            jvmTarget = javaVersion
            languageVersion = "1.3"
        }
    }

    withType<Test> {
        @Suppress("UnstableApiUsage")
        useJUnitPlatform()
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}

artifactory {
    publish(delegateClosureOf<PublisherConfig> {
        repository(delegateClosureOf<GroovyObject> {
            setProperty("repoKey", "libs-release-local-maven")
        })
        defaults(delegateClosureOf<GroovyObject> {
            invokeMethod("publications", "mavenJava")
        })
    })
}
