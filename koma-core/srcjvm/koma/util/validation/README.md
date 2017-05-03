# Matrix Validation DSL

This package provides definitions for a domain-specific language that you can use to validate matrices used as inputs to your functions in a consistent way. The rules throw detailed, human-readable exceptions using an appropriate Java exception class based on rules that look like this:

```kotlin
import koma.*
import koma.util.validation.*

fun mFunction(foo: Matrix<Double>, bar: Matrix<Double>, baz: Matrix<Double>) {
    validate {
        foo("foo") {  1  x 'N'; transposable }
        bar("bar") { 'N' x 'N'; symmetric }
        baz("baz") { 'N' x  1 ; max = 5 }
    }

    /* Your code here */
}
```

Some of the exceptions the above code could generate include:

```
java.lang.IndexOutOfBoundsException: Invalid matrix dimensions.

Matrix Required Actual 
====== ======== ====== 
foo       1xN     1x2  
bar       NxN     2x2  
baz       Nx1     2x3  

baz must have the same number of rows as foo has columns
baz must have the same number of rows as bar has rows
baz must have the same number of rows as bar has columns
baz must have exactly 1 columns (has 3)
```

or

```
java.lang.IndexOutOfBoundsException: bar must be symmetric, but has dimensions 1x2
```

or

```
java.lang.IllegalArgumentException: baz[0, 0] > 5.0 (value was 15.0)
```

## Annotated Syntax
```kotlin
fun myFunction(foo: Matrix<Double>, bar: Matrix<Double>, baz: Matrix<Double>) {
    validate { /*
        vvv ------------------------------------ Matrix to examine.
            vvvvv ------------------------------ Name to use in the exception.
                    vvvvvvvvvvvvvvvvvvvvvvvv --- Rules to check the matrix */
        foo("foo") { 1  x 'N'; transposable }
    }
}
```

## Rules
Rules are regular Kotlin statements, which can be separated either by semicolons or newlines. The rules block will be evaluated with an instance of `ValidaitonContext` as its receiver.

|      Syntax            |     Description                                          |
|------------------------|----------------------------------------------------------|
| `1 x 2` or `dim(1, 2)` | Verify the matrix has 1 row and 2 columns. Values can be any expression that evaluates to `Int`. The latter syntax is provided as an alternative if the order-of-operations for [infix functions](https://kotlinlang.org/docs/reference/functions.html#infix-notation) does something weird.  |
| `1 x 'N'`              | Verify the matrix has 1 row and any number of columns; compare the column count to other things that use the character `'N'`|
| `transposable`         | The given dimensions can be in either order. So, a `1 x 3; transposable` matrix can have either 1 row and 3 columns or 3 rows and 1 column. |
| `symmetric`            | Verify that the matrix is [symmetric](http://mathworld.wolfram.com/SymmetricMatrix.html) |
| `max = 4.0`            | Specify a maximum allowable value for individual coefficients in the matrix. Can be any expression that evaluates to `Double` |
| `min = 2.0`            | Specify a minimum allowable value for individual coefficients in the matrix. Can be any expression that evaluates to `Double` |


## Shorthand for a single Matrix
If you've written a function that only has a single Matrix argument, you can use this shorthand syntax instead of a full validate block.

```kotlin
fun myFunction(foo: Matrix<Double>) {
    foo.validate("foo") { 1 x 3; transposable }

    /* Your code here */
}
```

### A Cautionary Example
If you have more than one matrix, you should avoid the shorthand syntax because dimensions variables will not "stick" as you might expect. For example:

```kotlin
fun myFunction(foo: Matrix<Double>, bar: Matrix<Double>) {
    foo.validate("foo") {  1  x 'N'; transposable }
    bar.validate("bar") { 'N' x 'N' } // <-- WILL NOT WORK
    

    /* Your code here */
}
```

In that example, it will validate that `bar` is square, but not that its dimensions correspond with the number of columns in `foo` as you might expect. This is because each validate block allocates a separate `ValidationContext` that performs some of its validations at the end of the block.

## How it works
Behind the scenes, the validation code uses kotlin [extension methods](http://kotlinlang.org/docs/reference/extensions.html) to enable a syntax inspired by Kotlin's ["Type-safe builders" feature](http://kotlinlang.org/docs/reference/type-safe-builders.html).

Each of the validation rules you define is calling an extension method on the `ValidationContext` class.

If you'd like to extend the validation syntax yourself, you can do so by adding more extension methods to `ValidationContext`. `bounds.kt` probably offers the best example to work from.
