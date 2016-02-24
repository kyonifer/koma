### Golem

Golem is a scientific environment for Kotlin that emphasizes language interop, performance, and flexibility.

## Project Goals

The project aims to:

- Create a scientific programming environment that is friendly and easy for people used to NumPy or MATLAB
- Support usage from Kotlin, Java, other JVM languages, and foreign calls from Python or MATLAB
- Use pluggable back-ends to offload the actual computation
- Use other projects where possible to avoid duplication

## Features

Golem has two components: A set of flat functions which mimic the behavior of NumPy/MATLAB,
and an underlying object oriented hierarchy which dispatch the function calls. The flat functions are in the
top-level files [here](src/main/golem/) while the matrix implementations are [here](src/main/golem/matrix/).
It is easiest to get up to speed on Golem by reading through the available top-level functions.

We currently have:

- Integrated plotting (via xchart)

- Pluggable matrix back-end for pure-Java and high-performance modes (via MTJ and EJML)

- Many linear algebra operations supported.

## Example Usage

The following is an example of plotting some random noise. The golem.* namespace brings in all the
top-level functions (the only other import you may need is golem.matrix.Matrix if you need to type
  a function you write).

```Kotlin
import golem.*

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
![](https://raw.githubusercontent.com/kyonifer/golem/imgs/plotting.png)


We can also do some linear algebra:

```Kotlin
import golem.*

fun main(args: Array<String>)
{
    // Matrix literal syntax, see creators.kt for functions like zeros(5,5)
    var A = mat[1,2,3 end
                4,5,6 end
                7,8,9]

    var b = mat[2,2,4].T

    var c = A*b + 1

    // Number of decimals to show
    format("short")

    println(c)

}
```

Which produces:

```
Output:

000019.000  
000043.000  
000067.000
```
