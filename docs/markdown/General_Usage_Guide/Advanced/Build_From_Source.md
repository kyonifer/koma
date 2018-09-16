# Building Koma from Source

## Prerequisites

Building Koma from source requires:

* A JDK targeting Java 8 on the system path
* git

## Building

Grab a copy of the latest Koma code and enter the checked out directory:

```
git clone https://github.com/kyonifer/koma.git
cd koma
```

Now run the gradle build command, passing in a parameter specifying which platform you
are building for:

<!--names=JVM,JS,Native-->
```bash
# Produces .jars for all koma artifacts in ./build/jvm
./gradlew buildJvm
```
```bash
# Outputs commonjs modules in ./node_modules/
./gradlew buildJs
```
```bash
# Outputs the following into ./build/native/<your_platform>:
#   - komaKlib.klib, a bundle which can be used by other Kotlin/native projects
#   - libkoma.so/.dll/.dylib, a native library that can be used by native applications
#   - libkoma_api.h, a C header file which can be used to link against libkoma
#   - komaExample.kexe, a native executable that can be run directly and embeds Koma 
./gradlew buildNative
```

To verify success, run some test code:

<!--names=JVM,JS,Native-->
```bash
# Runs the unit tests
./gradlew clean test
```
```bash
# Runs a toy example assuming node is installed
node examples/js/example.js
```
```bash
# Runs the previously built example executable
./build/native/<your_platform>/komaExample.kexe
```

## Note About IDEs

Koma JS and JVM can be imported into the Intellij IDEA IDE. Koma Native can
be imported into CLion. In all cases, you can load Koma into the IDE by choosing
to import an existing project and then selecting the `settings.gradle` file in the
Koma root folder. 
