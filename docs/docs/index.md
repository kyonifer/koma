# Koma

A scientific computing library for Kotlin.

--------

### Overview


This project aims to:

- Create a scientific programming environment for Kotlin that is familiar to people used to NumPy or
MATLAB
- Support writing numerical code once in Kotlin, and then deploy that code on JVM, JS, and native platforms
- Support polyglot usage from Kotlin, Java, other JVM languages, as well as foreign interop with legacy Python or MATLAB code
- Use pluggable back-ends to offload the actual computation to pre-existing libraries, depending on the target platform
- Use code already written by other projects where possible to avoid duplication


### Quickstart

Koma is hosted on bintray. First add it to your repos:

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
}
```

And we're ready to go. Lets plot a random walk:

```Kotlin
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

### The Next Step
The `core` artifact uses an unoptimized backend by default. Check out [the backends page](guide/backends.md) 
to find a better backend on your target platform.
