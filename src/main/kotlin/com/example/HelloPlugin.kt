package com.example

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Exec

class HelloPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.afterEvaluate {
            project.tasks.register("hello", Exec::class.java) { task ->
                task.commandLine = listOf(
                    "echo",
                    "Hello, world!"
                )
            }
        }
    }
}
