# blitzy

![Kotlin](https://img.shields.io/badge/Kotlin-2.2.21-blue)
[![License: LGPL v3](https://img.shields.io/badge/License-LGPL_v3-blue.svg)](https://www.gnu.org/licenses/lgpl-3.0)
![Java](https://img.shields.io/badge/Java-21-red)
<br><br>
blitzy – a lightweight 2D game engine for kotlin/jvm<br>
entity, camera, world-space rendering and more:
<p>
  <img 
      src="https://cdn.discordapp.com/attachments/1363515282697752586/1460864524029530238/019ef79a-415f-40b4-8a0f-fff7b4669682.gif?ex=6968779c&is=6967261c&hm=5db465443cc479237f6d95732435fc7ddcd98f0a8153648273e789a965954f8a&"
      width="800"
  >
</p>

### getting started
start using blitzy right now by visiting the [documentation](https://github.com/xerooup/blitzy/tree/main/documentation)<br>

### how to install:
engine uses lwjgl 3.3.6
for the engine to work, dependencies need to be installed manually

`build.gradle.kts` example:
```kt
repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation("com.github.xerooup:blitzy2d:ACTUAL_VERSION")

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
```

### example games:
![Example](https://cdn.discordapp.com/attachments/1363515282697752586/1460134747119288340/image.png?ex=6965cff3&is=69647e73&hm=a2e86f2e579b52fa1cd59d341e063a3bdb5aab0ed1da333a4768f975d850b87b&)