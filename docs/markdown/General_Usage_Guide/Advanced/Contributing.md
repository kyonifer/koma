
## Building the source

Please see [the instructions for building from source](Build_From_Source.md)

## Unit Tests

Koma's unit tests are located [here](https://github.com/kyonifer/koma/tree/master/tests/test/koma).
You can verify that the project still builds successfully on all three targets by running

```
./gradlew test
./gradlew build -Ptarget=native
./gradlew build -Ptarget=js
```

Unit tests on platforms other than java aren't currently available.

## Building docs

To build the docs, you'll need the python mkdocs and themes packages:

```
pip install mkdocs
pip install mkdocs-bootswatch
```

You can now run `mkdocs serve` and connect to localhost:8000 to view 
the resulting docs as you make changes.

## Hacking on Koma with Intellij

By default, importing Koma's `settings.gradle` into Intellij will result in a 
project configured for the JVM. If you'd like to load it in a different mode, 
you can set `target=js` or `target=native` in `$GRADLE_HOME/gradle.properties`.

**Note**: Because native doesn't have official Intellij support yet, setting
`target=native` will cause importing Koma into Intellij to use [a jury rigged 
build file](https://github.com/kyonifer/koma/blob/master/buildscripts/build-native-intellij.gradle)
thats designed to trick Intellij into completion of some kotlin-native types. 

**Note**: Setting the target property in gradle.properties will also affect `./gradlew`
 behavior. Be sure to unset them if you later want the target property unset when building
 from the command line.
