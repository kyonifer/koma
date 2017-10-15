# Overview

Koma is a scientific computing library written in Kotlin, designed to allow development 
of cross-platform numerical applications targeting Javascript, Java (JVM), and embedded (native) platforms.

Project goals:

- Create a scientific programming environment that is similar in style to NumPy or MATLAB
- Enable writing numerical applications which can be deployed on JVM, JS, and native platforms
- Support using said applications from Python, MATLAB, Java, and other pre-existing codebases
- Use pluggable back-ends to enable optimized computation via pre-existing platform libraries

To get started, try the quickstart instructions below for your desired platform. After that,
take a look at the [functionality overview](General_Usage_Guide/Functionality_Overview.md) for a quick intro on what Koma provides.


### Quickstart (Java)

Koma is hosted on bintray. First add the koma repository to your repo list. If
using gradle:

```groovy
repositories { 
    maven { 
        url "http://dl.bintray.com/kyonifer/maven" 
        jcenter()
    } 
}
```

Now add a dependency on the `core` artifact:

```Groovy
dependencies{
    compile group: "koma", name:"core", version:"0.10"
    // Optional, uses EJML's optimized routines for matrix operations
    compile group: "koma", name:"backend-matrix-ejml", version: "0.10"
}
```

And we're ready to go. Lets plot a random walk:

```kotlin
import koma.*

fun main(args: Array<String>)
{

    // Create some normal random noise
    var a = randn(100,2)
    var b = cumsum(a)

    figure(1)
    // Second parameter is color
    plot(a, 'b', "First Run")
    plot(a+1, 'y', "First Run Offset")
    xlabel("Time (s)")
    ylabel("Magnitude")
    title("White Noise")

    figure(2)
    plot(b, 'g') // green
    xlabel("Velocity (lightweeks/minute)")
    ylabel("Intelligence")
    title("Random Walk")

}
```
![](https://raw.githubusercontent.com/kyonifer/koma/imgs/plotting.png)

### Quickstart (Javascript)

To use Koma from javascript you currently have to [build from source](General_Usage_Guide/Advanced/Build_From_Source.md).
After doing so, you should have commonjs modules in the `./node_modules/` folder. 
You can then use koma directly from javascript:

```javascript
koma = require('koma-core').koma

m = koma.randn(3,3)

console.log(m)
console.log(m.plus(m.timesScalar(5)))

m2 = koma.ones(3,4)

console.log(m2)
console.log(m2.plus(m2).minusScalar(3))
```

### Quickstart (Native)

To produce a native executable including koma you currently have to [build from source](General_Usage_Guide/Advanced/Build_From_Source.md). 
This will produce an executable called `Koma.kexe` which includes the koma library as
well as the toy example main function defined at `examples/native/main.kt`:

```
import koma.*
import koma.matrix.default.*

fun main(args: Array<String>) {
    
    factory = DefaultDoubleMatrixFactory()
    
    println("randn(5,6): \n${randn(5,6).repr()}")

    var a = mat[1,2,3 end
                4,5,6]
    
    var b = mat[2, 0 end
                0, 1 end
               -1, 5]
    
    println("a: \n${a.repr()}")
    println("b: \n${b.repr()}")
    println("a*b: \n${(a*b).repr()}")
    
}
```

You can run the executable directly, without any js or java runtime dependency:
```
./Koma.kexe
```
You can edit the binary by making modifications to `examples/native/main.kt` in the source tree.
Support for building shared or static libraries will be added as the kotlin-native backend matures
(currently in pre-release).


