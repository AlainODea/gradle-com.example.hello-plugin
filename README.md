# gradle-com.example.hello-plugin
Example Gradle Plug-in written in Kotlin

## Usage

build.gradle:
```groovy
plugins {
    id 'com.example.hello' version '0.1-SNAPSHOT'
}
```

build.gradle.kts:
```kotlin
plugins {
    id("com.example.hello") version "0.1-SNAPSHOT"
}
```

That will create the **hello** task.

Run the **hello** task with this command:
```bash
./gradlew hello
```
