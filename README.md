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

Matrices have useful map functions that return matrices for chaining operations (see [here](src/main/golem/extensions.kt) for a complete list).

```Kotlin

    // Create a 3x3 identity and then add 0.1 to all elements
    val x = eye(3) + 0.1

    // Map each element through a function that adds .01
    val y = x.mapMat { it + .01 }
    
    // Map each element through a function that adds or subtracts depending on the element index
    val z = x.mapMatIndexed { row, col, ele -> if (row > col) ele + 1 else ele - 1 }

    // Are there any elements greater than 1?
    val hasGreater = x.any { it > 1 }

    // Are all elements greater than 1?
    val allGreater = x.all { it > 1 }

    // Apply a function to a row at a time and store the outputs in a contiguous matrix
    val sins = x.eachRow { row -> sin(row) }
    
    // Print all elements greater than 1
    x.each { if (it>1) println(it) }
    
``` 

We can also do some linear algebra:

```Kotlin

    // Matrix literal syntax, see creators.kt for 
    // convenience functions like zeros(5,5)
    var A = mat[1,0,0 end
                0,3,0 end
                0,0,4]

    // Calculate the matrix inverse
    var Ainv = A.inv()

    var b = mat[2,2,4].T

    // Use overloaded operators:
    
    // * is matrix multiplication 
    var c = A*b + 1
    
    // emul is element-wise multiplication
    var d = (A emul A) + 1

    // Number of decimals to show
    format("short")

    println(c)

```

Which produces:

```
Output:

000019.000  
000043.000  
000067.000
```

Many special functions are supported (see [here](src/main/golem/matrixfuncs.kt) for a complete list):

```Kotlin

    val a = 2*eye(3)+.01
    
    a.chol()  // Cholesky decomposition
    a.det()   // Determinant
    a.diag()  // Diagonal vector
    a.inv()   // Matrix inverse
    a.norm()  // Matrix norm
        
    // ... and many more.
```

Scalar functions can be applied elementwise to matrices (see [here](src/main/golem/scalarfuncs.kt) for a complete list):

```Kotlin
    val x = create(0..100)/5.0  // Matrix of 0, 1/5, 2/5, ...
    val y = sin(x)              // Sin applied elementwise
    plot(y)                     // Plot of sin function
```

Matrix indexing and slicing is supported (see [here](src/main/golem/operators.kt) for a list of operators as well as the Matrix<T> type):

```Kotlin

    val x = randn(5,5)
    val y = x[0,0..4] // Grab the first row
    
    x[0..2,0..3] = zeros(3,4) // Set the upper-left 3x4 sub-matrix of x to zero

```

Matrix also implements Iterable<T>, so it inherits all the functions of that type:

```Kotlin
    val x = randn(5,5)
    
    // Adds all elements and returns sum
    x.reduce { x, y -> x+y }
    
    // Returns list of all elements greater than 4
    x.find { it > 4 }

```