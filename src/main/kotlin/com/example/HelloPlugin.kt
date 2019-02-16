package com.example

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Exec
import org.gradle.kotlin.dsl.*

class HelloPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.afterEvaluate {
            tasks.register<Exec>("hello") {
                commandLine = listOf(
                    "echo",
                    "Hello, world!"
                )
            }
        }
    }
}
