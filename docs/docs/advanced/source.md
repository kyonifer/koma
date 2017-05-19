# Building Koma from Source

### Prerequisites

Building Koma from source requires:

* A JDK targeting Java 8 on the system path
* git

### Building

Grab a copy of the latest Koma code and enter the checked out directory:

```
git clone https://github.com/kyonifer/koma.git
cd koma
```

Now run the gradle build command, passing in a parameter specifying which platform you
are building for:

```JVM
./gradlew build -Ptarget=kotlin
```
```JS
./gradlew build -Ptarget=js
```
```Native
./gradlew build -Ptarget=native
```

You can now run the example on the platform you chose:

```JS
node examples/js/example.js
```
```Native
./Koma.kexe
```