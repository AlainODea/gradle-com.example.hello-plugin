package com.example

import com.natpryce.hamkrest.assertion.*
import com.natpryce.hamkrest.*
import org.gradle.testkit.runner.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Test

import java.nio.file.*
import kotlin.test.*

internal class HelloPluginTest {

    private lateinit var tempDir: Path
    private lateinit var testProjectDir: Path

    @BeforeEach
    fun setUp() {
        tempDir = Files.createTempDirectory(javaClass.name)
        testProjectDir = Files.createDirectory(tempDir.resolve("test-proj"))
    }

    @AfterEach
    fun tearDown() {
        recursiveDelete(tempDir)
    }

    private fun recursiveDelete(path: Path) {
        if (Files.isDirectory(path)) {
            Files.list(path).forEach { recursiveDelete(it) }
        }
        Files.delete(path)
    }

    @Test
    fun dontChangeClassName() {
        assertEquals("com.example.HelloPlugin", HelloPlugin::class.java.canonicalName)
    }

    @Test
    fun hello_happy_path() {
        val buildFileBytes: ByteArray = """
            plugins {
                id 'java'
                id 'com.example.hello'
            }
        """.trimIndent().toByteArray(Charsets.UTF_8)
        val buildFile = Files.createFile(testProjectDir.resolve("build.gradle"))
        Files.write(buildFile, buildFileBytes)
        val result = GradleRunner.create()
            .withProjectDir(testProjectDir.toFile())
            .withDebug(true)
            .withArguments("hello")
            .withPluginClasspath()
            .build()
        assertThat(result.output, containsSubstring("BUILD SUCCESSFUL"))
    }
}
