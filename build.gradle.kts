plugins {
    kotlin("jvm") version "2.2.21"
}

group = "org.xeroup"
version = "0.3.3"

repositories {
    mavenCentral()
}

dependencies {
    val lwjglVersion = "3.3.6"

    implementation(platform("org.lwjgl:lwjgl-bom:$lwjglVersion"))

    // main modules
    implementation("org.lwjgl", "lwjgl")
    implementation("org.lwjgl", "lwjgl-glfw")
    implementation("org.lwjgl", "lwjgl-opengl")
    implementation("org.lwjgl", "lwjgl-stb")
    implementation("org.lwjgl", "lwjgl-freetype")
    implementation("org.lwjgl", "lwjgl-openal")

    implementation("org.joml:joml:1.10.7")

    // native libs for all platforms
    listOf(
        "natives-windows",
        "natives-linux",
        "natives-macos"
    ).forEach { native ->
        runtimeOnly("org.lwjgl", "lwjgl", classifier = native)
        runtimeOnly("org.lwjgl", "lwjgl-glfw", classifier = native)
        runtimeOnly("org.lwjgl", "lwjgl-opengl", classifier = native)
        runtimeOnly("org.lwjgl", "lwjgl-stb", classifier = native)
        runtimeOnly("org.lwjgl", "lwjgl-freetype", classifier = native)
        runtimeOnly("org.lwjgl", "lwjgl-openal", classifier = native)
    }
}

tasks.register<Jar>("fatJar") {
    archiveFileName.set("blitzy-${version}.jar")

    from(sourceSets.main.get().output)

    from({
        configurations.runtimeClasspath.get()
            .filter { it.name.endsWith(".jar") }
            .map { zipTree(it) }
    })

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

kotlin {
    jvmToolchain(21)
}