# Overview

Koma is a scientific computing library written in Kotlin, designed to allow development 
of multiplatform numerical applications targeting JavaScript, Java (JVM), and/or embedded (native) platforms.

Project goals:

- Create a scientific programming environment that is similar in style to NumPy or MATLAB
- Enable writing numerical applications which can be deployed on JVM, JS, and native platforms
- Avoid reinventing the wheel by delegating to platform specific back-ends when available

To get started, try the quickstart instructions below for your desired platform. After that,
take a look at the [linear algebra](General_Usage_Guide/Matrices_&_Linear_Algebra.md) 
or [N-D arrays](General_Usage_Guide/N-Dimensional_Arrays.md) sections to see some usage examples.


### Quickstart (Using from Kotlin/JVM or Kotlin/JS)

Koma is hosted on bintray. First add the koma repository to your repo list. If
using gradle:

```groovy
repositories { 
    maven { url "https://dl.bintray.com/kyonifer/maven" }
    jcenter()
}
```

Now add a dependency on the `koma-core` artifact and the plotting artifact:

<!--names=JVM,JS-->
```Groovy
dependencies{
    compile group: "com.kyonifer", name:"koma-core-ejml", version: "0.12"
    compile group: "com.kyonifer", name:"koma-plotting", version: "0.12"
}
```
```Groovy
dependencies{
    compile group: "com.kyonifer", name:"koma-core-js", version: "0.12"
}
```

And we're ready to go. Do a quick test:

<!--names=JVM,JS-->
```kotlin
import koma.*
import koma.extensions.*

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
```kotlin
import koma.*
import koma.extensions.*

fun main(args: Array<String>)
{

    // Create some normal random noise
    var a = randn(100,2)
    var b = cumsum(a)
    
    // On js, koma doesn't have built-in plotting, so we'll just print the value
    println(b[99,0])
}
```

On the JVM you should see:

![](https://raw.githubusercontent.com/kyonifer/koma/imgs/plotting.png)

### Quickstart (Raw JS)

You can also use Koma directly from JavaScript. Begin by [building Koma from source](General_Usage_Guide/Advanced/Build_From_Source.md).
After doing so, you should see CommonJs modules for Koma in the `./node_modules/` folder. 
You can then use these modules from an installation of Node.js. For example, to run 
the toy example main function defined at [examples/js/example.js](https://github.com/kyonifer/koma/blob/master/examples/js/example.js)
you would run the following in the Koma root folder:


```
node examples/js/example.js
```


### Quickstart (Native)

You can use Koma in a native executable without either a JS or JVM runtime available. 
Begin by [building Koma from source](General_Usage_Guide/Advanced/Build_From_Source.md). 
This will produce an executable called `build/native/komaExample.kexe` which 
includes the Koma library as well as the toy example main function 
defined at [examples/native/main.kt](https://github.com/kyonifer/koma/blob/master/examples/native/main.kt).


You can run the executable directly, without any js or java runtime dependency:

```
./build/konan/bin/linux/komaExample.kexe
```

You can edit the binary by making modifications to `examples/native/main.kt` in the source tree.
See the build from source section above for instructions on building shared or static libraries
which can be used from an application.


