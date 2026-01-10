# blitzy

![Kotlin](https://img.shields.io/badge/Kotlin-2.2.21-blue)
![Java](https://img.shields.io/badge/Java-21-red)
[![License: LGPL v3](https://img.shields.io/badge/License-LGPL_v3-blue.svg)](https://www.gnu.org/licenses/lgpl-3.0)
<br><br>
blitzy – a lightweight 2D game engine for kotlin/jvm<br>

### getting started
start using blitzy right now by visiting the [documentation](https://github.com/xerooup/blitzy/tree/main/documentation)

### how to install:
1. install fat jar from [releases](https://github.com/xerooup/blitzy/releases/latest)
2. move the downloaded file to `.../YourProject/blitzy/`
3. in `build.gradle.kts` add implementation:
```kt 
dependencies {
    implementation(files("blitzy/blitzy-VERSION.jar"))
}
```
or:
```groovy 
dependencies {
    implementation files('blitzy/blitzy-VERSION.jar')
}
```
###### recommendation: use gradle, it will be easier to add the library as a dependency there

