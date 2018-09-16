
## Building the source

Please see [the instructions for building from source](Build_From_Source.md)

## Unit Tests

Koma's unit tests are located [here](https://github.com/kyonifer/koma/tree/master/koma-tests/test/koma).
You can verify that the project still builds successfully on all three targets by running

```
./gradlew build
```

Unit tests on platforms other than the JVM aren't currently available.

## Building docs

To build the docs, you'll first need the python mkdocs and themes packages:

```
pip install mkdocs
pip install mkdocs-bootswatch
```

Then run the following in the Koma root folder:

```bash
./build_docs
```
