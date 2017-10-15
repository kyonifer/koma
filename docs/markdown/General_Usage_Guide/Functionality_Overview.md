# Functionality Overview

### Core Interfaces

Koma has 3 core interfaces that define the available data containers:


|Interface|Dimensions|Non-numerical Elements|Math Operations|Convenience Methods for Generation|
|-|-|-|-|-|
| **[`koma.matrix.Matrix<T>`](https://github.com/kyonifer/koma/blob/master/core/src/koma/matrix/Matrix.kt)**            | 2 |N|LinAlg + Elementwise|**[`koma.matrix.MatrixFactory<T>`](https://github.com/kyonifer/koma/blob/master/core/src/koma/matrix/MatrixFactory.kt)**|
| **[`koma.ndarray.NDArray<T>`](https://github.com/kyonifer/koma/blob/master/core/src/koma/ndarray/NDArray.kt)**          |  Arbitrary |Y|Elementwise|
| **[`koma.ndarray.NumericalNDArray<T>`](https://github.com/kyonifer/koma/blob/master/core/src/koma/ndarray/NumericalNDArray.kt)** | Arbitrary |N|Elementwise|**[`koma.ndarray.NumericalNDArrayFactory<T>`](https://github.com/kyonifer/koma/blob/master/core/src/koma/ndarray/NumericalNDArrayFactory.kt)**|


### Available Implementations

Koma consists of the `core` module and several (optional) platform optimized modules. The `core` module is always available on every
 platform, and contains unoptimized pure-Kotlin implementations of `NDArray`, `NumericalNDArray`, and `Matrix`. In the interest of not reinventing the wheel, 
the default implementation doesnt implement some of the advanced functionalities on `Matrix`, such as 
decompositions. For a complete implementation of the `Matrix` interface, you'll want to select an 
optimized backend available on your target platform. Currently optimized backends are only available
 for the JVM, with more on the way for JS and Native soon. Here's a summary of the current available backends:

| Artifact              | Supported Platforms  | Provided Classes | ...which implement (respectively) |
|-----------------------|----------------------|------------------------|------------------|
|  core                 | JVM, JS, Native      | `DefaultNDArray<T>`<br> Default**XX**Matrix*<br>Default**XX**MatrixFactory<br>Default**XX**NDArray<br>Default**XX**NDArrayFactory|`NDArray<T>`<br>Matrix&lt;**XX**&gt;<br>MatrixFactory&lt;**XX**&gt;<br>NumericalNDArray&lt;**XX**&gt;><br>NumericalNDArrayFactory&lt;**XX**&gt;|
|  backend-matrix-mtj   | JVM                  | `MTJMatrix`<br>`MTJMatrixFactory`| `Matrix<Double>`<br>`MatrixFactory<Double>`|
|  backend-matrix-ejml  | JVM                  | `EJMLMatrix`<br>`EJMLMatrixFactory`| `Matrix<Double>`<br>`MatrixFactory<Double>`|
|  backend-matrix-jblas | JVM                  | `JBlasMatrix`<br>`JBlasMatrixFactory`|`Matrix<Double>`<br>`MatrixFactory<Double>`|

where **XX** is any of `Int`, `Double`, `Long`, or `Float`. 

Each of the backends uses an external library optimized for the platform 
to do the actual computation. For example, backend-matrix-ejml uses the [EJML](https://ejml.org) library for matrix operations.
Thus the core interfaces above serve as a facade for dispatching work to platform-specific libraries in the backends, with the default
implementation in `core` acting as a fallback that is unoptimized but always available.

\* Only contains support for basic functionality like additions or multiplications. Please use 
an optimized backend for advanced features like matrix decompositions.


### Enabling a Backend (JVM)

Once you've chosen a backend you want to use, you can enable it by adding it to your dependencies.
For example, if you would like to use the Matrix container based on MTJ, you can add the corresponding
artifact to your build.gradle dependencies:

```
dependencies {
    compile group: "koma", name:"backend-matrix-mtj", version: "0.10"
    compile group: "koma", name:"core", version:"0.10"
}
```

Koma should now pick up the new backend and use it for the [top-level functions](Matrices_&_Linear_Algebra.md). Koma will always try to 
use a platform specific backend if one is available, and fallback to the default `core` implementations 
if that fails. Continue to the next section if you are interested in forcing which backend is used.


### Multiple Backends at Once

Koma supports using multiple backends simultaneously. This is useful if e.g.
you need to work with multiple libraries which require different matrix containers. 

To begin, list more than one backend in your build.gradle dependencies. For example,
to have both EJML and MTJ available you might write:

```
dependencies {
    compile group: "koma", name:"backend-matrix-mtj", version: "0.10"
    compile group: "koma", name:"backend-matrix-ejml", version: "0.10"
    compile group: "koma", name:"core", version:"0.10"
}
```

You can now change the backend being used by koma's [top-level functions](Matrices_&_Linear_Algebra.md) at
any time by setting a property in the koma namespace. In Kotlin this looks
like:

```kotlin
import koma.matrix.ejml.EJMLMatrixFactory
import koma.matrix.mtj.MTJMatrixFactory

// Make subsequent function calls use the EJML backend
koma.factory = EJMLMatrixFactory()

val a = zeros(3,3) // An EJMLMatrix

// Make subsequent function calls use the MTJ backend
// (doesnt affect previous returns)
koma.factory = MTJMatrixFactory()

val b = zeros(3,3) // Now returns an MTJMatrix
```

This property can be set from Java and other languages via
`koma.Options.setFactory(...)`. If not set, koma will default
to using MTJ, EJML, and then JBlas in that order, falling back to Default if
none of them are found.

It is also possible to create any matrix type manually by using the
corresponding factory. For example, even if `koma.factory` is set to
MTJMatrixFactory, you could write

```kotlin
val eFac = EJMLMatrixFactory()
val a = eFac.eye(3, 3)
val b = eFac.ones(3, 3)
println(a+b) // Uses EJML's addition algorithm, not MTJ's
```

### Interoperating with Other Linear Algebra Libraries

Suppose you are using the EJML backend, and you need to use some code that is
expecting to receive EJML's `SimpleMatrix` type. You can get ahold of the underlying
`SimpleMatrix` by using the `storage` property:

```kotlin
val a = EJMLMatrixFactory().ones(3,3)
somethingThatNeedsSimpleMatrices(a.storage)
```

This makes it easy to use libraries requiring different matrix
containers simultaneously.


