plugins {
    kotlin("jvm") version "2.2.21"
    `maven-publish`
    signing
}

group = "io.github.xerooup"
version = "0.3.6"

repositories {
    mavenCentral()
}

java {
    withSourcesJar()
    withJavadocJar()
}

dependencies {
    val lwjglVersion = "3.3.6"

    api(platform("org.lwjgl:lwjgl-bom:$lwjglVersion"))
    api("org.lwjgl:lwjgl")
    api("org.lwjgl:lwjgl-glfw")
    api("org.lwjgl:lwjgl-opengl")
    api("org.lwjgl:lwjgl-stb")
    api("org.lwjgl:lwjgl-freetype")
    api("org.lwjgl:lwjgl-openal")

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


publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])

            pom {
                name.set("blitzy")
                description.set("""
                    blitzy – a lightweight 2D game engine for kotlin/jvm
                    entity, camera, world-space rendering and more
                    """.trimIndent())
                url.set("https://github.com/xerooup/blitzy2d")

                licenses {
                    license {
                        name.set("LGPL-3.0 License")
                        url.set("https://www.gnu.org/licenses/lgpl-3.0.html")
                    }
                }

                developers {
                    developer {
                        id.set("xeroup")
                        name.set("xeroup")
                        url.set("https://github.com/xerooup")
                    }
                }

                scm {
                    url.set("https://github.com/xerooup/blitzy2d")
                    connection.set("scm:git:https://github.com/xerooup/blitzy2d.git")
                    developerConnection.set("scm:git:ssh://github.com/xerooup/blitzy2d.git")
                }
            }
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications["maven"])
}

kotlin {
    jvmToolchain(21)
}