# N-Dimensional Containers

Koma has two N-D container interfaces, **NDArray** and **NumericalNDArray**.

### NDArray

`NDArray` is designed to hold high dimensional data of arbitrary type. Because its not guaranteed
to be 2D or numerical, it does not have facilities for doing any mathematics.

The default implementation of `NDArray` is `koma.ndarray.default.DefaultNDArray`, available in core (and thus
on every target platform). You can create a `DefaultNDArray` by giving it a vararg of index lengths and
an init function:

```kotlin
// Construct a 5x3x4 NDArray, and populate it using the passed function
val a: NDArray<Int> = DefaultNDArray(5, 3, 4) { idx -> idx[0] * 2 + idx[1] * 3 }

assert(a[1,2,1] == 1*2 + 2*3)
assert(a[3,1,3] == 3*2 + 1*3)
```

You can iterate over each of the elements with knowledge of their N-D index coordinates:

```kotlin
val a: NDArray<Int> = DefaultNDArray(2,4) { idx -> idx[0] * 2 + idx[1] * 3 }

a.forEachIndexedN { indices, value ->
    println("Element at ${indices.joinToString(",")} is $value")
}
```

You can also map elements to another NDArray:

```kotlin
val a: NDArray<Int> = DefaultNDArray(2,4) { idx -> idx[0] * 2 + idx[1] * 3 }

val b = a.map { value -> value + 1 }
```

The full set of functionality can be seen [here](https://github.com/kyonifer/koma/blob/master/core/src/koma/ndarray/NDArray.kt).
For both `map` and `forEach`, `IndexedN` at the end of the function name indicates you'd like to receive
a N dimensional index, and `Indexed` indicates you'd like a linear index:

```kotlin
val a: NDArray<Int> = DefaultNDArray(2,4) { idx -> idx[0] * 2 + idx[1] * 3 }

// No index provided
a.map { value -> value + 1 }
a.forEach { value -> print(value + 1) }
// Linear index of element provided
a.mapIndexed { value, idx -> value+idx }
a.forEachIndexed { value, idx -> print(value+idx) }
// N-D index of element provided
a.mapIndexedN { value, indices -> value + indices[0] }
a.forEachIndexedN { value, indices -> print(value + indices[0]) }
```

You can also convert an NDArray into an iterator (this will produce each element 
in the same order as the linear index `forEach` would have):

```kotlin
val a: NDArray<Int> = DefaultNDArray(2,4) { idx -> idx[0] * 2 + idx[1] * 3 }
a.toIterable()
```

And get the shape of the current container:

```kotlin
val a: NDArray<Int> = DefaultNDArray(2,4) { idx -> idx[0] * 2 + idx[1] * 3 }
val dims: List<Int>  = a.shape()
```

### NumericalNDArray

`NumericalNDArray` is a subtype of NDArray that guarantees its element type
will be numerical. Because of this, we can now perform math operations
on the array, and provide optimized (non-boxed) implementations for various primitives. The
core provides Default**XX**NDArray and Default**XX**NDArrayFactory implementations,
where **XX** is any of `Int`, `Double`, `Long`, or `Float`. For example, if we wanted
to work in single precision we could write:

```kotlin
// Create a 3x4x5 array filled with the number 1, stored as floats
val a = DefaultFloatNDArrayFactory().ones(3,4,5)

// Create a 2x8 array filled with the number 1, stored as coubles
val b = DefaultDoubleNDArrayFactory().randn(2, 8)
```

Just like in the non-numerical case, we can instead initialize a `NumericalNDArray`
by a init function:

```kotlin
val arr = DefaultFloatNDArray(3,5,4) { idx -> idx[0].toFloat() }
```

Because we are now guaranteed numerical elements, arithmetic is available:

```kotlin
val arr = DefaultDoubleNDArray(3,5,4) { idx -> idx[0].toDouble() }
// All operations are elementwise
val out = arr + arr
val out2 = arr - arr*4.0 - 1.5
```

The full list of operators available is [here](https://github.com/kyonifer/koma/blob/master/core/src/koma/ndarray/NumericalNDArray.kt).
Note that linear algebra operations are not available as `NumericalNDArray` is not
guaranteed to be 2D.


### Conversions between types

There are some conversions available between types. If a `NDArray`'s `T` value 
is known in the current context to be numerical, it can be converted to a `NumericalNDArray`:

```kotlin
val a: NDArray<Int> = DefaultNDArray(2,4) { idx -> idx[0] * 2 + idx[1] * 3 }

// Because we know the element type is Int, 
// the toNumerical extension function becomes available
val b: NumericalNDArray<Int> = a.toNumerical()
```


