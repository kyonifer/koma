# N-Dimensional Containers

## Creating NDArrays

`NDArray` is designed to hold high dimensional data of arbitrary type. 
Koma provides several functions for creating new `NDArray`s:

```kotlin
// Creates a 3x4x5 container of type NDArray<String> filled with nulls
NDArray.createGenericNulls<String>(3,4,5)
// Creates a 3x4x5 container of type NDArray<String> filled with "hello"
NDArray.createGeneric(3,4,5) { "hello" }
// Creates a 1x2 container of type NDArray<String> where each element's value is "hi" concatenated with the sum of its indices 
NDArray.createGeneric(1,2) { indices -> "hi ${indices.sum()}" }
// Creates a 3x4x5 container of type NDArray<Float> with each element set to 4.5
NDArray.createGeneric(3,4,5) { 4.5f }
``` 

As you can see, NDArray is capable of storing numerical and non-numerical data. However, storing numerical data the way
that was shown in the last example is very inefficient as each element is boxed. You should therefore use the optimized
factories if your NDArray is known to contain numerical primitives:

```kotlin
// Creates a 3x5x6 NDArray<Double> filled with zeros backed by a non-boxing Array<Double>
NDArray.doubleFactory.zeros(3,5,6)
// Creates a 3x5x6 NDArray<Float> filled with uniformly random numbers backed by a non-boxing Array<Float>
NDArray.floatFactory.rand(3,5,6)
// Creates a 1x2x3x4x5 NDArray<Double> filled with ones backed by a non-boxing Array<Int>
NDArray.intFactory.ones(1,2,3,4,5)
// Creates a 8x8 NDArray<Double> filled with normally distributed random numbers backed by a non-boxing Array<Double>
NDArray.doubleFactory.randn(8,8)
```

## Iteration

Each element in an `NDArray` has two indices: 

- Its N-dimensional index, which is an array of N numbers specifying the its N-dimensional location in the array
- Its linear index, which is a single number specifying its location in a flattened 1-dimensional version of the array
  
You can iterate over `NDArray`s with either index:

```kotlin
val a: NDArray<Double> = NDArray.doubleFactory.randn(3,5,6)

// Iterate without an index present
a.forEach { println("Element is $it") }

// Iterate with the linear index available
a.forEachIndexed { idx, ele -> println("Element at $idx is $ele") }

// Iterate with the N-dimensional index array available
a.forEachIndexedN { indices, value ->
    println("Element at ${indices.joinToString(",")} is $value")
}
```

You can also map elements to another NDArray with either the full N-D index or a linear index:

```kotlin
val a: NDArray<Float> = NDArray.floatFactory.ones(3,5,6)
// Adds one to all elements
a.map { ele -> ele + 1.0f }
// Adds the linear index to the element's value
a.mapIndexed { idx, ele -> ele + idx }
// Sums the element's N-dimensional index and sets the value to it
a.mapIndexedN { idx, ele -> idx.sum().toFloat() }
```

The full set of functionality can be seen [here](https://github.com/kyonifer/koma/blob/master/koma-core-api/common/src/koma/extensions/extensions_ndarray_Double.kt).
For `map` and `forEach`, `IndexedN` at the end of the function name indicates you'd like to receive
a N dimensional index, and `Indexed` indicates you'd like a linear index.

You can also convert an NDArray into an iterator (this will produce each element 
in the same order as the linear index `forEach` would have):

```kotlin
val a: NDArray<Float> = NDArray.floatFactory.ones(3,5,6)
a.toIterable()
```
## Array Shape

You can easily request the shape of the current container:

```kotlin
val a: NDArray<Float> = NDArray.floatFactory.ones(3,5,6)
a.shape() // arrayOf(3,5,6)
```

You can also reshape the current container:
```kotlin
val a: NDArray<Float> = NDArray.floatFactory.rand(3,5,6)
val b = a.reshape(6,3,5)
println(a.shape()) // arrayOf(3,5,6)
println(b.shape()) // arrayOf(6,3,5)
```

However, you cannot reshape if the number of elements in the new shape doesn't match the original:

```kotlin
val a: NDArray<Float> = NDArray.floatFactory.rand(3,5,6)
a.reshape(6,6,6) // Error: Not enough elements in the original to populate this one

```

Reshaping always maintains the linear index of elements, but reinterprets the N-dimensional index of each element
to fit the new shape. Thus a linear iteration of a reshaped container will be exactly the same as the original 
container:

```kotlin
val a: NDArray<Float> = NDArray.floatFactory.rand(3,5,6)
val b = a.reshape(6,3,5).toIterable().iterator()
val c = a.reshape(1,6*3*5).toIterable().iterator()

a.toIterable().forEach {
    assert(it == b.next() && it == c.next())
}
```

## Numerical Operations

If an NDArray's element type is numerical, numerical operations will be available to you. If you created
your NDArray using the [optimized factories mentioned previously](N-Dimensional_Arrays.md#creating-ndarrays) these
operations will also be non-boxing:

```kotlin
val a = NDArray.floatFactory.rand(3,5,6)
val b = 3 * a + a * a
```

Note that linear algebra operations are not available as `NDArray` is not
guaranteed to be 2D. If you know your container is 2D, you'll want to [convert it to a Matrix](N-Dimensional_Arrays.md#conversions-between-types).

### Conversions between types

As `NDArray` is a supertype of `Matrix`, any `Matrix` can be passed into a method expecting
an `NDArray`. To convert `NDArray`s to `Matrix`, you may use the `toMatrix` extension function:

```kotlin
val a = NDArray.floatFactory.rand(3,6)
a.toMatrix()
```

Note that `toMatrix` is only available if the element type is known (i.e. `NDArray`<Float> is okay, `NDArray`<T> is not) and
will only be successful if the input `NDArray` has 1 or 2 dimensions. If you have a generic `NDArray` or are unsure how many
dimensions the container has, you can use the `toMatrixOrNull` form:

```kotlin
// Returns null, too many dimensions
println(NDArray.floatFactory.rand(3,5,6).toMatrixOrNull())
// OK, 2 dimensions and numerical
println(NDArray.doubleFactory.rand(3,6).toMatrixOrNull())
// Returns null, String is not numeric
println(NDArray.createGenericNulls<String>(3,4).toMatrixOrNull())
// Returns null, String is not numeric
println(NDArray.createGeneric(3,4){"hi"}.toMatrixOrNull())
// OK, 2 dimensions and numerical
println(NDArray.createGeneric(3,4){1.4}.toMatrixOrNull())

// OK, toMatrixOrNull available for generic Matrices
fun <T>foo(a: Matrix<T>) = a.toMatrixOrNull()
// Error, "a" isn't known to be numeric
fun <T>foo(a: Matrix<T>) = a.toMatrix()
```


