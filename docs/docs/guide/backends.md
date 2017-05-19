# Available Backends

The core koma artifact contains a unoptimized default implementation of the ND and Matrix 
containers. In the interest of not reinventing the wheel, the default implementation doesnt 
implement many of the advanced matrix functionalities, such as decompositions. For a complete
implementation of the matrix interface, you'll want to select an optimized backend available 
on your target platform. Currently optimized backends are only available for the JVM, with 
more on the way for JS and Native soon:


| Artifact              | Supported Platforms  | Implemented Interfaces | Relevant Classes |
|-----------------------|----------------------|------------------------|------------------|
|  core                 | JVM, JS, Native      | `Matrix<T>`*, `NDArray<T>`, `NumericalNDArray<T>` | `DefaultNDArray<T>`, `DefaultDoubleNDArray`, `DefaultFloatNDArray`, `DefaultIntNDArray` |
|  backend-matrix-mtj   | JVM                  | `Matrix<Double>`         | `MTJMatrix`|
|  backend-matrix-ejml  | JVM                  | `Matrix<Double>`         | `EJMLMatrix`|
|  backend-matrix-jblas | JVM                  | `Matrix<Double>`         | `JBlasMatrix`|



\* Only contains support for basic functionality like additions or multiplications. Please use 
an optimized backend for advanced features like matrix decompositions.


### Multiple Backends at Once

Koma supports using multiple backends simultaneously. This is useful if e.g.
you need to work with multiple libraries which require different matrix containers. 

You can change the backend being used by koma's top-level functions at
any time by setting a property in the koma namespace. In Kotlin this looks
like:

```kotlin
import koma.matrix.ejml.EJMLMatrixFactory
...

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

This makes it easy to use libraries requiring different matrix
containers simultaneously.
