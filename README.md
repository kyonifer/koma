[![GitHub issues](https://img.shields.io/github/issues/kyonifer/koma.svg?maxAge=2592000)](https://github.com/kyonifer/koma/issues)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)
[![Bintray](https://img.shields.io/bintray/v/kyonifer/maven/koma-core.svg?maxAge=2592000)](https://bintray.com/kyonifer/maven)
[![Travis](https://img.shields.io/travis/kyonifer/koma/master.svg)](https://travis-ci.org/kyonifer/koma)
[![AppVeyor](https://img.shields.io/appveyor/ci/kyonifer/koma/master.svg)](https://ci.appveyor.com/project/kyonifer/koma)
### Koma

Koma is a scientific computing environment for Kotlin that emphasizes language/platform interop, performance, and flexibility.

## Project goals:

- Create a scientific programming environment that is similar in style to NumPy or MATLAB
- Enable writing numerical applications which can be deployed on JVM, JS, and native platforms
- Support using said applications from Python, MATLAB, Java, and other pre-existing codebases
- Use pluggable back-ends to enable optimized computation via pre-existing platform libraries

## Project documentation

For more information on using Koma, please see the [documentation](https://kyonifer.github.io/koma)

## Building

To build from source, use one of the following commands:

```
# Java
./gradlew build
# Javascript
./gradlew build -Ptarget=js
# Native (example executable, see examples/native/main.kt)
./gradlew compileKonanKomaExample -Ptarget=native
# Native (.dll/.h) (**requires building kotlin-native from master**)
./gradlew compileKonanLibkoma -Ptarget=native -Pkonan.home=/path/to/kotlin-native/dist
# Native (.klib)
./gradlew compileKonanKoma -Ptarget=native
```

For more information see [building from source](http://koma.kyonifer.com/General_Usage_Guide/Advanced/Build_From_Source/index.html).

## Related Projects

Koma has backends that wrap several other numerical projects on the JVM:

* Pure Java linear algebra: http://ejml.org/
* Pluggable native libs: https://github.com/fommil/matrix-toolkits-java
* Blas wrapper: http://jblas.org/

For a data analysis library similar to pandas, check out https://github.com/holgerbrandl/kplyr

For Kotlin statistical methods, check out https://github.com/thomasnield/kotlin-statistics
