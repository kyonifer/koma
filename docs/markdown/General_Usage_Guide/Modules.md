# Modules

Koma consists of several modules:

- [`koma-core-api`](Modules.md#koma-core-api): Definition of fundamental `Matrix` and `NDArray` containers and top-level convenience functions that mimic Numpy/MATLAB functionality
- [`koma-core`](Modules.md#koma-core-implementations): Implementations of `koma-core-api`, with varying implementations available depending on platform
- [`koma-plotting`](Modules.md#koma-plotting): Implementation of 2D plotting of data in `koma-core` containers
- `koma-logging`: *(deprecated now that alternative multiplatform logging libraries exist)*

### koma-core-api
Koma has two core interfaces that define the available data containers:

|Interface|Dimensions|Non-numerical Elements|Math Operations|Convenience Methods for Generation|
|-|-|-|-|-|
| **[`koma.matrix.Matrix<T>`](https://github.com/kyonifer/koma/blob/master/koma-core-api/common/src/koma/matrix/Matrix.kt)**            | 2 |N|LinAlg + Elementwise|**[`Matrix.doubleFactory`](https://github.com/kyonifer/koma/blob/master/koma-core-api/common/src/koma/matrix/Matrix.kt#L35)** <br> **[`Matrix.floatFactory`](https://github.com/kyonifer/koma/blob/master/koma-core-api/common/src/koma/matrix/Matrix.kt#L49)** <br> **...** |
| **[`koma.ndarray.NDArray<T>`](https://github.com/kyonifer/koma/blob/master/koma-core-api/common/src/koma/ndarray/NDArray.kt)**          |  Arbitrary |Y|Elementwise|**[`NDArray.createGeneric(...)`](https://github.com/kyonifer/koma/blob/master/koma-core-api/common/src/koma/ndarray/NDArray.kt#L60)** <br> **[`NDArray.doubleFactory`](https://github.com/kyonifer/koma/blob/master/koma-core-api/common/src/koma/ndarray/NDArray.kt#L30)** <br> **[`NDArray.floatFactory`](https://github.com/kyonifer/koma/blob/master/koma-core-api/common/src/koma/ndarray/NDArray.kt#L35)** <br> **...**|

`NDArray` is a N-dimensional container that can hold arbitrary data types, with optimized implementations provided if they contain primitives. 
`Matrix` is a container that is restricted to 2D and numerical types, but enables linear algebra operations which can be accelerated 
by available platform libraries.

The `koma-core-api` module defines all of the functionality that a `koma-core` implementation
must provide. If you are writing a library that uses Koma and wish to allow the user to select their own core 
implementation, you can depend on the API artifact (`com.kyonifer:koma-core-api-{platform}:{version}`) only, thus allowing
the user to select the core implementation when they depend on your library.

### koma-core Implementations 

An implementation of `koma-core` will provide implementations of the `koma-core-api`; more specifically, it must implement
`Matrix`, `MatrixFactory`, and `NDArray`.
 
Koma provides a set of `koma-core` implementations you can choose from. All of the implementations currently share an implementation
of `NDArray`, but delegate to different platform-specific libraries for linear algebra operations on `Matrix`. 
Here's a summary of the currently available backends:

| Artifact              | Supported Platforms  | Provided Classes | ...which implement (respectively) |
|-----------------------|----------------------|------------------------|------------------|
|  koma-core-js         | JS                   | `DefaultDoubleMatrix`*<br>`DefaultDoubleMatrixFactory` | `Matrix<Double>`<br>`MatrixFactory<Double>`
|  koma-core-mtj        | JVM                  | `MTJMatrix`<br>`MTJMatrixFactory`| `Matrix<Double>`<br>`MatrixFactory<Double>`|
|  koma-core-ejml       | JVM                  | `EJMLMatrix`<br>`EJMLMatrixFactory`| `Matrix<Double>`<br>`MatrixFactory<Double>`|
|  koma-core-jblas      | JVM                  | `JBlasMatrix`<br>`JBlasMatrixFactory`|`Matrix<Double>`<br>`MatrixFactory<Double>`|
|  koma-core-cblas      | Native               | `CBlasMatrix`<br>`CBlasMatrixFactory`|`Matrix<Double>`<br>`MatrixFactory<Double>`|

Each of the backends uses an external library optimized for the platform 
to do the actual computation. For example, backend-matrix-ejml uses the [EJML](https://ejml.org) library for matrix operations.
Thus the core interfaces above serve as a facade for dispatching work to platform-specific libraries in the backends.

\* Only contains support for basic functionality like additions or multiplications.

### koma-plotting

Currently a WIP and only on the JVM, `koma-plotting` provides a barebones plotting capability. 

## Enabling Backends

### On Kotlin/Native

On Kotlin/Native the CBlas matrix backend is automatically included in the built artifacts by default,
so no additional action is needed. The `CBlasMatrix` it provides will delegate work to the available
blas and lapack libraries on your system.

### On Kotlin/JVM or Kotlin/JS

Once you've chosen which backend you want to use, you can enable it by adding it to your gradle dependencies.
For example, if you would like to use the Matrix container based on MTJ, you can add the corresponding
artifact listed above to your build.gradle dependencies:

```
dependencies {
    compile group: "com.kyonifer", name:"koma-core-mtj", version: $komaVersion
}
```

Koma should now pick up the new backend and use it for the 
[top-level functions](Matrices_&_Linear_Algebra.md) with no further action required. 
Continue to the next section if you are interested in forcing which backend is used.


### Multiple Backends at Once

Koma supports using multiple backends simultaneously. This is useful if e.g.
you need to work with multiple libraries which require different matrix containers. 

To begin, list more than one backend in your build.gradle dependencies. For example,
to have both EJML and MTJ available you might write:

```
dependencies {
    compile group: "koma", name:"koma-core-mtj", version: $komaVersion
    compile group: "koma", name:"koma-core-ejml", version: $komaVersion
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
to using MTJ, EJML, and then JBlas in that order.

It is also possible to create any matrix type manually by using the
corresponding factory. For example, even if `koma.factory` is set to
MTJMatrixFactory, you could write

```kotlin
val eFac = EJMLMatrixFactory()
val a = eFac.eye(3, 3)
val b = eFac.ones(3, 3)
println(a+b) // Uses EJML's addition algorithm, not MTJ's
```

## Interoperating with Other Linear Algebra Libraries

Suppose you are using the EJML backend, and you need to use some code that is
expecting to receive EJML's `SimpleMatrix` type. You can get ahold of the underlying
`SimpleMatrix` by using the `storage` property:

```kotlin
val a = EJMLMatrixFactory().ones(3,3)
somethingThatNeedsSimpleMatrices(a.storage)
```

This makes it easy to use libraries requiring different matrix
containers simultaneously.


