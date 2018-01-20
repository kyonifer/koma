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
./gradlew build

# Optionally, build a fat jar with all dependencies included to ./build/libs/
./gradlew shadowJar
```
```bash
# Outputs commonjs modules in ./node_modules/
./gradlew build -Ptarget=js
```
```bash
# Outputs a linked executable with main supplied by examples/native/main.kt
# The exectuable is located at build/konan/bin/linux/komaExample.kexe
./gradlew compileKonanKomaExample -Ptarget=native

# Outputs a dynamic lib (.so) and a header (.h) supporting using koma from
# C. Requires kotlin-native to be built from master (until next k/native release).
# The generated files are located at build/konan/bin/linux/libkoma.so and 
# build/konan/bin/linux/libkoma_api.h
./gradlew compileKonanLibkoma -Ptarget=native -Pkonan.home=/path/to/kotlin-native/dist

# Outputs a kotlin library (.klib) that can be imported to other kotlin/native projects
# The .klib is located at build/konan/libs/linux/koma.klib
./gradlew compileKonanKoma -Ptarget=native

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
./build/konan/bin/linux/komaExample.kexe
```

## Note About IDEs

If you use an IDE, make sure that you run the gradle `:codegen` 
task when building Koma from source. This is done for you when using gradle 
directly, but your IDEs build system will likely not know that it needs to. The
`:codegen` task generates the primitive-optimized implementations from the 
[implementation templates](https://github.com/kyonifer/koma/tree/master/core/templates).
If you are getting errors such as `DefaultDoubleMatrix` not existing, its probably
because the `:codegen` task has yet to run.
